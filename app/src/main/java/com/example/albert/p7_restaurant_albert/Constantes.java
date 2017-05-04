package com.example.albert.p7_restaurant_albert;

/**
 * Created by Albert on 03/04/2017.
 */

public class Constantes {
    /*** Puerto que utilizas para la conexión ***/
    private static final String PUERTO_HOST = "";
    ///trabajoWebService/WebService_PHP
    private static final String rutaPadre = "/M15_UF2/P7_WebService";

    /*** Dirección IP del emulador o IP Web Service ***/
    //private static final String IP = "http://10.0.2.2";
    private static final String IP = "http://10.10.0.26";
    //private static final String IP = "http://10.0.2.15:";

    /*** URLs del Web Service ***/
    public static final String POST_LOG_IN = IP + PUERTO_HOST +rutaPadre+ "/index.php";

    public static final String ACCEDER_CUINER_PLATS = IP + PUERTO_HOST +rutaPadre+ "/cuiner/llistatPlats.php";
    public static final String ACCEDER_MAITRE_PLATS = IP + PUERTO_HOST +rutaPadre+ "/maitre/llistatPlats.php";

    /*** Clave para el valor extra que representa al identificador de una cita ***/
    public static final String EXTRA_ID = "IDEXTRA";
}
