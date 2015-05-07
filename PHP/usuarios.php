<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
//include 'conexion.php';

function imprimirError($error){
	$response = array();
	// required field is missing
    $response["ERROR"] = $error;
    // echoing JSON response
    echo json_encode($response);
    //exit;
}

function IDENTIFICAR($email, $contraseña){
	require_once __DIR__ . '/conexion.php';
	$resultado = mysqli_query($con, "SELECT id FROM Usuarios WHERE email='".$email."' AND contrasena='".$contraseña."';");
	if(!$resultado){
    		imprimirError("Error (1) mysql: ".mysqli_error($con));
    }else{
    	$response = array();
    	if($fila = mysqli_fetch_row($resultado)){
    		$response['ID'] = $fila[0];
    	}
    	cerrarConexion($con);
    	echo json_encode($response);
    }
}

function REGISTRAR($id, $email, $contraseña){
	require_once __DIR__ . '/conexion.php';
	//comprobamos si el id esta ya siendo usado por otra persona
    //echo "REGISTRAR";
	$resultado = mysqli_query($con, "SELECT id FROM Usuarios WHERE id='".$id."';");
	if(!$resultado){
        //echo "IF1";
    	imprimirError("Error (1) mysql: ".mysqli_error($con));
    }else{
        //echo "ELSE1";
    	if($fila = mysqli_fetch_row($resultado)){
            //echo "IF2";
    		imprimirError("El nombre introducido ya esta siendo usado. Por favor, selecciona otro.");
    	}else{
            //echo "ELSE2";
    		//Si no esta siendo usado
    		//comprobamos que el email no esta siendo usado por otro usuario
    		$resultado = mysqli_query($con, "SELECT email FROM Usuarios WHERE email='".$email."';");
			if(!$resultado){
                //echo "IF3";
    			imprimirError("Error (2) mysql: ".mysqli_error($con));
    		}else{	
                //echo "ELSE3";
    			if($fila = mysqli_fetch_row($resultado)){
                    //echo "IF4";
    				imprimirError("El email introducido ya esta siendo usado. Por favor, selecciona otro.");
    			}else{
                    //e/cho "ELSE4";
    				//si no lo esta 
    				//realizamos el registro.
                    //echo "INSERT INTO Usuarios (id, email, contrasena) VALUES ('".$id."', '".$email."', '".$contraseña."');";
    				$resultado = mysqli_query($con, "INSERT INTO Usuarios (id, email, contrasena) VALUES ('".$id."', '".$email."', '".$contraseña."');");
					if(!$resultado){
                        //echo "IF5";
                        //echo "Error (3) mysql: ".mysqli_error($con);
						imprimirError("Error (3) mysql: ".mysqli_error($con));
    				}else{
                        //echo "ELSE5";
    					$response = array();
                        $response["OK"] = "Registro realizado con exito.";
                        echo json_encode($response);
    				}
    			}
    		}
    	}
    	
    }
    cerrarConexion($con);
}

if (isset($_POST['FUNCION']) ) {
	$funcion = $_POST['FUNCION'];

    //separamos por funciones
    if($funcion == 'selLineas'){

    	listarLineas();

    }elseif($funcion == 'IDENTIFICAR'){

    	//nos aseguramos de que estan todos los parametros necesarios, o sino emitimos error
    	if(isset($_POST['EMAIL']) && isset($_POST['CONTRASENA'])){
    		IDENTIFICAR($_POST['EMAIL'], $_POST['CONTRASENA']);
    	}else{
    		imprimirError("No se ha definido un parametro necesario para la funcion identificar");
    	}

    }elseif($funcion == 'REGISTRAR'){

    	//nos aseguramos de que estan todos los parametros necesarios, o sino emitimos error
    	if(isset($_POST['EMAIL']) && isset($_POST['CONTRASENA']) && isset($_POST['ID'])){
    		REGISTRAR($_POST['ID'], $_POST['EMAIL'], $_POST['CONTRASENA']);
    	}else{
    		imprimirError("No se ha definido un parametro necesario para la funcion REGISTRAR");
            if(!isset($_POST['EMAIL'])){
                imprimirError("No se ha definido el parametro EMAIL necesario para la funcion REGISTRAR");
            }
            if(!isset($_POST['ID'])){
                imprimirError("No se ha definido el parametro ID necesario para la funcion REGISTRAR");
            }
            if(!isset($_POST['CONTRASENA'])){
                imprimirError("No se ha definido el parametro ID necesario para la funcion REGISTRAR");
            }
    	}
    }else{
    	//imprimimos un error si la funcion que se busca no esta definida
    	imprimirError("La funcion no esta definida");
    }

}else{
	imprimirError("No se ha definido la variable funcion");
}
?>