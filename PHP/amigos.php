<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

function imprimirError($error){
	$response = array();
	// required field is missing
    $response["ERROR"] = $error;
    // echoing JSON response
    echo json_encode($response);
    //exit;
}

function DESCARGAR($jugador){
	require_once __DIR__ . '/conexion.php';
	$response = array();
	$response['AMIGOS'] = array();
	$resultado = mysqli_query($con, "SELECT id FROM Usuarios WHERE id != '".$jugador."' AND id NOT IN (SELECT idAmigo FROM Amigos WHERE idJugador =  '".$jugador."' );");
	if(!$resultado){
        imprimirError("Error (1) mysql: ".mysqli_error($con));
    }else{
        while($fila = mysqli_fetch_row($resultado)){
            array_push($response['AMIGOS'], $fila[0]);
        }
    }
    cerrarConexion($con);
    echo json_encode($response);
}

function ANADIR($jugador, $amigo){
	require_once __DIR__ . '/conexion.php';
	$resultado = mysqli_query($con, "INSERT INTO Amigos(idJugador, idAmigo) VALUES ('".$jugador."', '".$amigo."');");
	if(!$resultado){
        imprimirError("Error (1) mysql: ".mysqli_error($con));
    }else{
        $response = array();
    	$response["OK"] = "OK";
   		echo json_encode($response);
    }
    cerrarConexion($con);
}

function getFecha(){
    $hoy = getdate();
    $fecha = $hoy['year']."-".$hoy['mon']."-".$hoy['mday']." ".$hoy['hours'].":".$hoy['minutes'].":".$hoy['seconds'];
    return $fecha;
}

function ENVIAR($destinatario, $lat, $lon, $obj){
	require_once __DIR__ . '/conexion.php';
	$fecha = getFecha();
	$resultado = mysqli_query($con, "INSERT INTO Envios(idJugador, idObjeto, lat, lon, cuando, recibido) VALUES ('".$destinatario."', '".$obj."', '".$lat."', '".$lon."', '".$fecha."', 0);");
	if(!$resultado){
        imprimirError("Error (1) mysql: ".mysqli_error($con));
    }else{
        $response = array();
    	$response["OK"] = "OK";
   		echo json_encode($response);
    }
    cerrarConexion($con);
}

if (isset($_POST['FUNCION']) ) {
	$funcion = $_POST['FUNCION'];
    //separamos por funciones
    if($funcion == 'DESCARGAR'){
    	if(isset($_POST['ID'])){
    		DESCARGAR($_POST['ID']);
    	}else{
    		imprimirError("La funcion no dispone de los parametros necesarios");
    	}
    }elseif($funcion == 'ANADIR'){
    	if(isset($_POST['ID']) && isset($_POST['AMIGO'])){
    		ANADIR($_POST['ID'], $_POST['AMIGO']);
    	}else{
    		imprimirError("La funcion no dispone de los parametros necesarios");
    	}
    }elseif($funcion == 'ENVIAR'){
    	if(isset($_POST['AMIGO']) && isset($_POST['LAT']) && isset($_POST['LON']) && isset($_POST['OBJETO'])){
    		ENVIAR($_POST['AMIGO'], $_POST['LAT'], $_POST['LON'], $_POST['OBJETO']);
    	}else{
    		imprimirError("La funcion no dispone de los parametros necesarios");
    	}
    }else{
    	//imprimimos un error si la funcion que se busca no esta definida
    	imprimirError("La funcion no esta definida");
    }
}else{
	imprimirError("No se ha definido la variable funcion");
}
?>
