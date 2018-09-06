package com.example.cecy_.arbolito;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cecy_ on 01/08/2018.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String TableAccion = "CREATE TABLE Accion(idAccion INTEGER PRIMARY KEY AUTOINCREMENT, accion VARCHAR);";
    private static final String TableCambio = "CREATE TABLE Cambio(idCambio INTEGER PRIMARY KEY AUTOINCREMENT, idEntrega INTEGER, idProducto INTEGER, precioProducto REAL, cantidadCambio INTEGER, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableCliente = "CREATE TABLE Cliente(idCliente INTEGER, idTipoNegocio INTEGER, idRuta INTEGER, nombrePropietario VARCHAR, nombreNegocio VARCHAR, domicilio VARCHAR, colonia VARCHAR, ciudad VARCHAR, telefono VARCHAR, observacion VARCHAR, notaCobrar INTEGER, bono REAL, latitud VARCHAR, longitud VARCHAR, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')), estado INTEGER);";
    private static final String TableClienteDiaEntrega = "CREATE TABLE ClienteDiaEntrega(idCliente INTEGER, idDia INTEGER);";
    private static final String TableClientesExtra = "CREATE TABLE ClientesExtra(idClientesExtra INTEGER PRIMARY KEY AUTOINCREMENT, idProductoAsig INTEGER, idCliente INTEGER, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableDiaEntrega = "CREATE TABLE DiaEntrega(idDia INTEGER, day VARCHAR, number INTEGER, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableEmpleado = "CREATE TABLE Empleado(idEmpleado INTEGER, idTipoEmpleado INTEGER, nombreEmpleado VARCHAR, telefonoEmpleado VARCHAR, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableEntrega = "CREATE TABLE Entrega(idEntrega INTEGER PRIMARY KEY AUTOINCREMENT, idCliente INTEGER, idAccion INTEGER, idEntregaRuta INTEGER, venta REAL, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')), fechaEntrega DATE);";
    private static final String TableEntregaRuta = "CREATE TABLE EntregaRuta(idEntregaRuta INTEGER PRIMARY KEY AUTOINCREMENT, idRuta INTEGER, fechaEntrega DATE, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableModificacion = "CREATE TABLE Modificacion(idModificacion INTEGER PRIMARY KEY AUTOINCREMENT, idUsuario INTEGER, idFilaModificada INTEGER, tableName VARCHAR, idName VARCHAR, columnName VARCHAR, tipoModificacion INTEGER, createdOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableNotaCobrar = "CREATE TABLE NotaCobrar(idNota INTEGER PRIMARY KEY AUTOINCREMENT, idCliente INTEGER, cantidad REAL, folio VARCHAR, cantidadPago REAL, isPagada INTEGER, fechaNota DATE, fechaCobrar DATE, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')), sync INTEGER);";
    private static final String TablePagoNota= "CREATE TABLE PagoNota(idPago INTEGER PRIMARY KEY AUTOINCREMENT, idNota INTEGER, idUsuario INTEGER, cantidadPago REAL, createdOn TIMESTAMP DEFAULT (DATETIME('now')), sync INTEGER);";
    private static final String TableProduccion = "CREATE TABLE Produccion(idProduccion INTEGER PRIMARY KEY AUTOINCREMENT, idRuta INTEGER, idSabor INTEGER, idProducto INTEGER, precio REAL, cantidadLitro INTEGER, cantidadLitroDevolucion INTEGER, fechaProduccion DATE, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableProduccionDiaSiguiente = "CREATE TABLE ProduccionDiaSiguiente(idProduccion INTEGER PRIMARY KEY AUTOINCREMENT, idSabor INTEGER, idProducto INTEGER, precio REAL, cantidadLitro INTEGER, cantidadLitroDevolucion INTEGER, cantidadLitroInventario INTEGER, fechaProduccion DATE, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableProduccionDiaSiguienteExt = "CREATE TABLE ProduccionDiaSiguienteExt(idProduccion INTEGER PRIMARY KEY AUTOINCREMENT, idSabor INTEGER, idProducto INTEGER, precio REAL, cantidadLitro INTEGER, cantidadLitroDevolucion INTEGER, cantidadLitroInventario INTEGER, fechaProduccion DATE, sucursal INTEGER, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableProduccionExt = "CREATE TABLE ProduccionExt(idProduccion INTEGER PRIMARY KEY AUTOINCREMENT, idRuta INTEGER, idSabor INTEGER, idProducto INTEGER, precio REAL, cantidadLitro INTEGER, cantidadLitroDevolucion INTEGER, fechaProduccion DATE, sucursal INTEGER, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableProduccionSalida = "CREATE TABLE ProduccionSalida(idProduccion INTEGER PRIMARY KEY AUTOINCREMENT, idSabor INTEGER, idProducto INTEGER, nombreSalida VARCHAR, precio REAL, cantidadLitro INTEGER, cantidadLitroDevolucion INTEGER, fechaProduccion DATE, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableProduccionSalidaExt = "CREATE TABLE ProduccionSalidaExt(idProduccion INTEGER PRIMARY KEY AUTOINCREMENT, idSabor INTEGER, idProducto INTEGER, nombreSalida VARCHAR, precio REAL, cantidadLitro INTEGER, cantidadLitroDevolucion INTEGER, fechaProduccion DATE, sucursal INTEGER, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableProducto = "CREATE TABLE Producto(idProducto INTEGER PRIMARY KEY AUTOINCREMENT, producto VARCHAR, precio REAL, descripcion VARCHAR, inTable INTEGER, ordenProducto INTEGER, litros REAL, ordenProduccion INTEGER, inTableProduccion INTEGER, factorBulto INTEGER, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableProductoAsig = "CREATE TABLE ProductoAsig(idProductoAsig INTEGER, idUsuario INTEGER, idRuta INTEGER, fecha DATE);";
    private static final String TableProductoAsignacion = "CREATE TABLE ProductoAsignacion(idProductoAsignacion INTEGER PRIMARY KEY AUTOINCREMENT, idProductoAsig INTEGER, idPresentacion INTEGER, idSabor INTEGER, cantidad INTEGER, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableRuta = "CREATE TABLE Ruta(idRuta INTEGER, idSupervisor INTEGER, idResponsable INTEGER, idAsistente INTEGER, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')), nombreRuta VARCHAR);";
    private static final String TableRutasExt = "CREATE TABLE RutasExt(idRuta INTEGER, idSucursal INTEGER);";
    private static final String TableSabor = "CREATE TABLE Sabor(idSabor INTEGER, sabor VARCHAR, orden INTEGER, inTable INTEGER, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableSucursal = "CREATE TABLE Sucursal(idSucursal INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR, activo INTEGER, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')));";
    private static final String TableTipoEmpleado = "CREATE TABLE TipoEmpleado(idTipoEmpleado INTEGER PRIMARY KEY AUTOINCREMENT, tipoEmpleado VARCHAR);";
    private static final String TableTipoNegocio = "CREATE TABLE TipoNegocio(idTipoNegocio INTEGER PRIMARY KEY AUTOINCREMENT, tipoNegocio VARCHAR);";
    private static final String TableUsuario = "CREATE TABLE Usuario(idUsuario INTEGER, usuario VARCHAR, nombreUsuario VARCHAR, contrasena VARCHAR, md5 VARCHAR, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')), tipoUsuario INTEGER, sucursal INTEGER);";
    private static final String TableVentasClientes = "CREATE TABLE VentasClientes(idVentasClientes INTEGER PRIMARY KEY AUTOINCREMENT, idCliente INTEGER, idProducto INTEGER, ventas INTEGER, cambios INTEGER, cortesia INTEGER, devolucion INTEGER, danado INTEGER, precio FLOAT, ventaNo INTEGER, fecha DATE, createdOn TIMESTAMP DEFAULT (DATETIME('now')), updatedOn TIMESTAMP DEFAULT (DATETIME('now')), sync INTEGER);";

    //public static String SERVER_URL = "http://192.168.1.67/arbolito/"; //Agregar nombre del archivo

    public static String SERVER_URL = "https://cp.aarbolito.com/archivosApp/"; //Agregar nombre del archivo
    public static final String UI_UPDATE_BROADCAST = "com.example.cecy_.arbolito.uiupdatebroadcast";
    public static final String DROP = "DROP TABLE IF EXISTS ";

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date = new Date();

    public static final String DATABASE_NAME = "contactdb";
    public static final String SYNC_STATUS = "sync";

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableAccion);
        db.execSQL(TableCambio);
        db.execSQL(TableCliente);
        db.execSQL(TableClienteDiaEntrega);
        db.execSQL(TableClientesExtra);
        db.execSQL(TableDiaEntrega);
        db.execSQL(TableEmpleado);
        db.execSQL(TableEntrega);
        db.execSQL(TableEntregaRuta);
        db.execSQL(TableModificacion);
        db.execSQL(TableNotaCobrar);
        db.execSQL(TablePagoNota);
        db.execSQL(TableProduccion);
        db.execSQL(TableProduccionDiaSiguiente);
        db.execSQL(TableProduccionDiaSiguienteExt);
        db.execSQL(TableProduccionExt);
        db.execSQL(TableProduccionSalida);
        db.execSQL(TableProduccionSalidaExt);
        db.execSQL(TableProducto);
        db.execSQL(TableProductoAsig);
        db.execSQL(TableProductoAsignacion);
        db.execSQL(TableRuta);
        db.execSQL(TableRutasExt);
        db.execSQL(TableSabor);
        db.execSQL(TableSucursal);
        db.execSQL(TableTipoEmpleado);
        db.execSQL(TableTipoNegocio);
        db.execSQL(TableUsuario);
        db.execSQL(TableVentasClientes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP + "Accion");
        db.execSQL(DROP + "Cambio");
        db.execSQL(DROP + "Cliente");
        db.execSQL(DROP + "ClienteDiaEntrega");
        db.execSQL(DROP + "ClientesExtra");
        db.execSQL(DROP + "DiaEntrega");
        db.execSQL(DROP + "Empleado");
        db.execSQL(DROP + "Entrega");
        db.execSQL(DROP + "EntregaRuta");
        db.execSQL(DROP + "Modificacion");
        db.execSQL(DROP + "NotaCobrar");
        db.execSQL(DROP + "PagoNota");
        db.execSQL(DROP + "Produccion");
        db.execSQL(DROP + "ProduccionDiaSiguiente");
        db.execSQL(DROP + "ProduccionDiaSiguienteExt");
        db.execSQL(DROP + "ProduccionExt");
        db.execSQL(DROP + "ProduccionSalida");
        db.execSQL(DROP + "ProduccionSalidaExt");
        db.execSQL(DROP + "Producto");
        db.execSQL(DROP + "ProductoAsig");
        db.execSQL(DROP + "ProductoAsignacion");
        db.execSQL(DROP + "Ruta");
        db.execSQL(DROP + "RutasExt");
        db.execSQL(DROP + "Sabor");
        db.execSQL(DROP + "Sucursal");
        db.execSQL(DROP + "TipoEmpleado");
        db.execSQL(DROP + "TipoNegocio");
        db.execSQL(DROP + "Usuario");
        db.execSQL(DROP + "VentasClientes");
        onCreate(db);
    }

    /*public void saveToLocalDatabase(String name, int sync_status, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContact.NAME, name);
        contentValues.put(DbContact.SYNC_STATUS, sync_status);
        database.insert(DbContact.TABLE_NAME, null, contentValues);
    }*/

    public void saveToLocalDatabaseVentas(int idCliente, int idProducto, int ventas, int cambios, int cortesia, int danado, float precio, int ventaNo, String fecha, int sync, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("idCliente", idCliente);
        contentValues.put("idProducto", idProducto);
        contentValues.put("ventas", ventas);
        contentValues.put("cambios", cambios);
        contentValues.put("cortesia", cortesia);
        contentValues.put("danado", danado);
        contentValues.put("precio", precio);
        contentValues.put("ventaNo", ventaNo);
        contentValues.put("fecha", fecha);
        contentValues.put("sync", sync);
        database.insert("VentasClientes", null, contentValues);
    }

    public void saveToLocalDatabaseClientes(int idCliente, int idTipoNegocio, int idRuta, String nombrePropietario, String nombreNegocio, String domicilio, String colonia, String ciudad, String telefono, int notaCobrar, double bono, String latitud, String longitud, int estado, SQLiteDatabase database){
        //database.execSQL("DELETE FROM cliente");
        ContentValues contentValues = new ContentValues();
        contentValues.put("idCliente", idCliente);
        contentValues.put("idTipoNegocio", idTipoNegocio);
        contentValues.put("idRuta", idRuta);
        contentValues.put("nombrePropietario", nombrePropietario);
        contentValues.put("nombreNegocio", nombreNegocio);
        contentValues.put("domicilio", domicilio);
        contentValues.put("colonia", colonia);
        contentValues.put("ciudad", ciudad);
        contentValues.put("telefono", telefono);
        contentValues.put("notaCobrar", notaCobrar);
        contentValues.put("bono", bono);
        contentValues.put("latitud", latitud);
        contentValues.put("longitud", longitud);
        contentValues.put("estado", estado);
        database.insert("cliente", null, contentValues);
    }

    public void saveToLocalDatabaseUsuario(int idUsuario, String usuario, String nombreUsuario, String contrasena, String md5, int tipoUsuario, int Sucursal, SQLiteDatabase database){
        //database.execSQL("DELETE FROM usuario");
        ContentValues contentValues = new ContentValues();
        contentValues.put("idUsuario", idUsuario);
        contentValues.put("usuario", usuario);
        contentValues.put("nombreUsuario", nombreUsuario);
        contentValues.put("contrasena", contrasena);
        contentValues.put("md5", md5);
        contentValues.put("tipoUsuario", tipoUsuario);
        contentValues.put("Sucursal", Sucursal);
        database.insert("usuario", null, contentValues);
    }

    public void saveToLocalDatabaseProductoAsig(int idProductoAsig, int idUsuario, int idRuta, String fecha, SQLiteDatabase database){
        //database.execSQL("DELETE FROM productoAsig");
        ContentValues contentValues = new ContentValues();
        contentValues.put("idProductoAsig", idProductoAsig);
        contentValues.put("idUsuario", idUsuario);
        contentValues.put("idRuta", idRuta);
        contentValues.put("fecha", fecha);
        database.insert("productoAsig", null, contentValues);
    }

    public void saveToLocalDatabaseNotaCobrar(int idNota, int idCliente, double cantidad, String folio, double cantidadPago, int isPagada, String fechaNota, String fechaCobrar, SQLiteDatabase database){
        //database.execSQL("DELETE FROM notacobrar");
        ContentValues contentValues = new ContentValues();
        contentValues.put("idNota", idNota);
        contentValues.put("idCliente", idCliente);
        contentValues.put("cantidad", cantidad);
        contentValues.put("folio", folio);
        contentValues.put("cantidadPago", cantidadPago);
        contentValues.put("isPagada", isPagada);
        contentValues.put("fechaNota", fechaNota);
        contentValues.put("fechaCobrar", fechaCobrar);
        database.insert("notacobrar", null, contentValues);
    }

    public void saveToLocalDatabasePagoNota(int idNota, int idUsuario, double cantidadPago, int sync, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("idNota", idNota);
        contentValues.put("idUsuario", idUsuario);
        contentValues.put("cantidadPago", cantidadPago);
        contentValues.put("sync", sync);
        database.insert("pagonota", null, contentValues);
    }

    public Cursor readFromLocalDatabaseClientes(SQLiteDatabase database){
        String[] projection = {"idCliente", "idTipoNegocio", "idRuta", "nombrePropietario", "nombreNegocio", "domicilio", "colonia", "ciudad", "telefono", "notaCobrar", "bono", "latitud", "longitud", "estado"};
        return(database.query("Cliente", projection, null, null, null, null, null));
    }

    public Cursor readFromLocalDatabaseVentas(SQLiteDatabase database){
        String[] projection = {"idVentasClientes", "idCliente", "idProducto", "ventas", "cambios", "cortesia", "devolucion", "danado", "precio", "ventaNo", "fecha", "createdOn", "updatedOn", "sync"};
        return (database.query("ventasClientes", projection, null, null, null, null, null));
    }

    public Cursor readFromLocalDatabaseAbonos(SQLiteDatabase database){
        String[] projection = {"idPago", "idNota", "idUsuario", "cantidadPago", "createdOn", "sync"};
        return (database.query("pagonota", projection, null, null, null, null, null));
    }

    public void updateLocalDatabase(int idCliente, int sync_status, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync", sync_status);
        String selection = "idCliente" + " LIKE ?";
        String[] selection_args = {String.valueOf(idCliente)};
        database.update("ventasClientes", contentValues, selection, selection_args);
    }

    public void updateLocalDatabaseAbonos(int idCliente, int sync_status, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync", sync_status);
        String selection = "idPago" + " LIKE ?";
        String[] selection_args = {String.valueOf(idCliente)};
        database.update("pagonota", contentValues, selection, selection_args);
    }
}
