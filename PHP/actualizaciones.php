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

function GetInfoNiveles($con, $fecha){
    $niveles = array();
    if($fecha != null){
        $resultado = mysqli_query($con, "SELECT nivel, experiencia FROM Niveles WHERE creacion >= '".$fecha."';");
    }else{
        $resultado = mysqli_query($con, "SELECT nivel, experiencia FROM Niveles;");
    }
    if(!$resultado){
        imprimirError("Error (1) mysql: ".mysqli_error($con));
    }else{
        while($fila = mysqli_fetch_row($resultado)){
            $array = array();
            $array['nivel'] = $fila[0];
            $array['experiencia'] = $fila[1];
            array_push($niveles, $array);
        }
    }
    return $niveles;
}

function getInfoObjetos($con, $fecha){
    $objetos = array();
    if($fecha != null){
        $resultado = mysqli_query($con, "SELECT id, nombre, minLevel, vida, ataque, defensa, cabeza, torso, piernas, pies, arma FROM Objetos WHERE creacion >= '".$fecha."';");
    }else{
        $resultado = mysqli_query($con, "SELECT id, nombre, minLevel, vida, ataque, defensa, cabeza, torso, piernas, pies, arma FROM Objetos;");
    }
    if(!$resultado){
        imprimirError("Error (1) mysql: ".mysqli_error($con));
    }else{
        while($fila = mysqli_fetch_row($resultado)){
            $array = array();
            $array['id'] = $fila[0];
            $array['nombre'] = $fila[1];
            $array['minLevel'] = $fila[2];
            $array['vida'] = $fila[3];
            $array['ataque'] = $fila[4];
            $array['defensa'] = $fila[5];
            $array['cabeza'] = $fila[6];
            $array['torso'] = $fila[7];
            $array['piernas'] = $fila[8];
            $array['pies'] = $fila[9];
            $array['arma'] = $fila[10];
            array_push($objetos, $array);
        }
    }
    return $objetos;
}
function getInfoCombinaciones($con, $fecha){
    $combinaciones = array();
    if($fecha != null){
        $resultado = mysqli_query($con, "SELECT objeto1, objeto2, resultado FROM Combinaciones WHERE creacion >= '".$fecha."';");
    }else{
        $resultado = mysqli_query($con, "SELECT objeto1, objeto2, resultado FROM Combinaciones;");
    }
    if(!$resultado){
        imprimirError("Error (1) mysql: ".mysqli_error($con));
    }else{
        while($fila = mysqli_fetch_row($resultado)){
            $array = array();
            $array['objeto1'] = $fila[0];
            $array['objeto2'] = $fila[1];
            $array['resultado'] = $fila[2];
            array_push($combinaciones, $array);
        }
    }
    return $combinaciones;
}

function GetInfoEnemigos($con, $fecha){
    $enemigos = array();
    if($fecha != null){
        $resultado = mysqli_query($con, "SELECT nombre, vida, ataque, defensa, experiencia FROM Enemigos WHERE creacion >= '".$fecha."';");
    }else{
        $resultado = mysqli_query($con, "SELECT nombre, vida, ataque, defensa, experiencia FROM Enemigos;");
    }
    if(!$resultado){
        imprimirError("Error (1) mysql: ".mysqli_error($con));
    }else{
        while($fila = mysqli_fetch_row($resultado)){
            $array = array();
            $array['nombre'] = $fila[0];
            $array['vida'] = $fila[1];
            $array['ataque'] = $fila[2];
            $array['defensa'] = $fila[3];
            $array['experiencia'] = $fila[4];
            array_push($enemigos, $array);
        }
    }
    return $enemigos;
}

function GetInfoEnvios($con, $jugador){
    $envios = array();
    $resultado = mysqli_query($con, "SELECT idObjeto, lat, lon FROM Envios WHERE idJugador='".$jugador."' AND recibido= 0;");
    if(!$resultado){
        imprimirError("Error (1) mysql: ".mysqli_error($con));
    }else{
        while($fila = mysqli_fetch_row($resultado)){
            $array = array();
            $array['idObjeto'] = $fila[0];
            $array['lat'] = $fila[1];
            $array['lon'] = $fila[2];
            array_push($envios, $array);
        }
        $resultado = mysqli_query($con, "UPDATE Envios SET recibido=1 WHERE idJugador='".$jugador."' AND recibido= 0;");
    }
    return $envios;
}

function getFecha(){
    $hoy = getdate();
    $fecha = $hoy['year']."-".$hoy['mon']."-".$hoy['mday']." ".$hoy['hours'].":".$hoy['minutes'].":".$hoy['seconds'];
    return $fecha;
}

function INSTALACION($jugador){
    require_once __DIR__ . '/conexion.php';
    $response = array();
    $response['NIVELES'] = GetInfoNiveles($con, null);
    $response['OBJETOS'] = GetInfoObjetos($con, null);
    $response['COMBINACIONES'] = GetInfoCombinaciones($con, null);
    $response['ENVIOS'] = GetInfoEnvios($con, $jugador);
    $response['ENEMIGOS'] = GetInfoEnemigos($con, null);
    $response['FECHA'] = GetFecha();
    cerrarConexion($con);
    echo json_encode($response);
}

function ACTUALIZACION($fecha, $jugador){
    require_once __DIR__ . '/conexion.php';
    $response = array();
    $response['NIVELES'] = GetInfoNiveles($con, $fecha);
    $response['OBJETOS'] = GetInfoObjetos($con, $fecha);
    $response['COMBINACIONES'] = GetInfoCombinaciones($con, $fecha);
    $response['ENVIOS'] = GetInfoEnvios($con, $jugador);
    $response['ENEMIGOS'] = GetInfoEnemigos($con, $fecha);
    $response['FECHA'] = GetFecha();
    cerrarConexion($con);
    echo json_encode($response);
}




if (isset($_POST['FUNCION']) ) {
	$funcion = $_POST['FUNCION'];
    //separamos por funciones
    if($funcion == 'INSTALACION'){
        if(isset($_POST['ID'])){
            INSTALACION($_POST['ID']);
        }else{
            imprimirError("No se ha definido un parametro necesario para la funcion INSTALACION");
        }
    }elseif($funcion == 'ACTUALIZACION'){
    	//nos aseguramos de que estan todos los parametros necesarios, o sino emitimos error
    	if(isset($_POST['FECHA']) && isset($_POST['ID'])){
    		ACTUALIZACION($_POST['FECHA'], $_POST['ID']);
    	}else{
    		imprimirError("No se ha definido un parametro necesario para la funcion ACTUALIZACION");
    	}
    }else{
    	//imprimimos un error si la funcion que se busca no esta definida
    	imprimirError("La funcion no esta definida");
    }
}else{
	imprimirError("No se ha definido la variable funcion");
}
?>
