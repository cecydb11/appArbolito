package com.example.cecy_.arbolito;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;


public class BluetoothPrint {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date = new Date();
    String fecha =  dateFormat.format(date);
    public static ArrayList<String> arrayListImp = new ArrayList<>();
    Context context;
   // String cliente,fecha,total;
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;

    OutputStream outputStream;
    InputStream inputStream;
    Thread thread;
    Resources resources;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    public static final byte[] ESC_ALIGN_LEFT = new byte[] { 0x1b, 'a', 0x00 };
    public static final byte[] ESC_ALIGN_RIGHT = new byte[] { 0x1b, 'a', 0x02 };
    public static final byte[] ESC_ALIGN_CENTER = new byte[] { 0x1b, 'a', 0x01 };
    public static final byte[] ESC_CANCEL_BOLD = new byte[] { 0x1B, 0x45, 0 };

    public BluetoothPrint(Context context) {
        this.context = context;
    }

    public void FindBluetoothDevice(){
        try{
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(bluetoothAdapter==null){
                Toast.makeText(context,"No se encontró ningún dispositivo Bluetooth.",Toast.LENGTH_LONG).show();
              //  lblPrinterName.setText("No Bluetooth Adapter found");
            }
            if(bluetoothAdapter.isEnabled()){
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                context.startActivity(enableBT);
            }

            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
            arrayListImp.clear();
            if(pairedDevice.size()>0){
                for(BluetoothDevice pairedDev:pairedDevice){
                    //Toast.makeText(context,pairedDev.getName().toString(),Toast.LENGTH_LONG).show();
                    // My Bluetoth printer name is BTP_F09F1A

                    //List para imprimir
                    arrayListImp.add(pairedDev.getName());
                    //Toast.makeText(context,"nombre: " + pairedDev.getName(),Toast.LENGTH_LONG).show();

                    //if(pairedDev.getName().equals("BlueTooth Printer")){
                    /*if(pairedDev.getName().equals(crearVenta.nombreImpresora)){
                        bluetoothDevice = pairedDev;
                        Toast.makeText(context,"Bluetooth Conectado Correctamente",Toast.LENGTH_LONG).show();
                        //lblPrinterName.setText("Bluetooth Printer Attached: "+pairedDev.getName());
                        break;
                    }*/
                }
            }

            //lblPrinterName.setText("Bluetooth Printer Attached");
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    // Open Bluetooth Printer

    public void openBluetoothPrinter() throws IOException {
        try{
            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
            for(BluetoothDevice pairedDev:pairedDevice){
                //if(pairedDev.getName().equals("BlueTooth Printer")){
                if(pairedDev.getName().equals(crearVenta.nombreImpresora)){
                    bluetoothDevice = pairedDev;
                    Toast.makeText(context,"Bluetooth Conectado Correctamente",Toast.LENGTH_LONG).show();
                    //lblPrinterName.setText("Bluetooth Printer Attached: "+pairedDev.getName());
                    break;
                }
            }
            //Standard uuid from string //
            UUID uuidSting = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuidSting);
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();
            inputStream=bluetoothSocket.getInputStream();

            beginListenData();

        }catch (Exception ex){

        }
    }

    public boolean checkConnection(){
        if(bluetoothSocket!=null){
            if(bluetoothSocket.isConnected()){
                Toast.makeText(context,"Estoy aqui esta conectado",Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return false;
    }

    public void beginListenData(){
        try{
            final Handler handler =new Handler();
            final byte delimiter=10;
            stopWorker =false;
            readBufferPosition=0;
            readBuffer = new byte[1024];

            thread=new Thread(new Runnable() {
                @Override
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker){
                        try{
                            int byteAvailable = inputStream.available();
                            if(byteAvailable>0){
                                byte[] packetByte = new byte[byteAvailable];
                                inputStream.read(packetByte);

                                for(int i=0; i<byteAvailable; i++){
                                    byte b = packetByte[i];
                                    if(b==delimiter){
                                        byte[] encodedByte = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer,0,
                                                encodedByte,0,
                                                encodedByte.length
                                        );
                                        final String data = new String(encodedByte,"US-ASCII");
                                        readBufferPosition=0;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                //lblPrinterName.setText(data);
                                            }
                                        });
                                    }else{
                                        readBuffer[readBufferPosition++]=b;
                                    }
                                }
                            }
                        }catch(Exception ex){
                            stopWorker=true;
                        }
                    }

                }
            });

            thread.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void printData(ArrayList<DetallePedido> arrayList,String cliente, int idCliente, String total) throws  IOException{
        try{
            Toast.makeText(context,"Imprimiendo.",Toast.LENGTH_LONG).show();
           //printPhoto(R.drawable.logoticket);
            String msg = "\r\nArbolito\r\n";
            msg += "\r\n";
            msg += "\r\nFecha: "+fecha+"  \r\n";
            msg+= "\r\nArandas, Jalisco\r\n";
            msg += "\r\n";
            msg += "------------------------------";
            msg += "\r\n";
            msg += "\r\nCliente: " + cliente + " #" + idCliente + "\r\n";
            outputStream.write(ESC_ALIGN_CENTER);
            outputStream.write(msg.getBytes());
            msg = "\r\n"+cliente+" \r\n";
            msg += "\r\n------------------------------\r\n";
            msg += "\r\n";
            msg += "\r\nCANT.   DESCRIPCION   IMP   \r\n";
            msg += "\r\n";
            msg += "\r\n------------------------------\r\n";
            outputStream.write(msg.getBytes());
            msg = "\r\n";
            for(int i=0; i < arrayList.size(); i++){
                if(arrayList.get(i).getCant() != 0) {
                    msg += "\r\n" + arrayList.get(i).getCant() + "   " + arrayList.get(i).getDesc() + " $" + arrayList.get(i).getImp() + ".00  \r\n";
                    msg += "\r\n";
                    msg += "\r\n";
                }
            }
            msg += "\r\n";
            outputStream.write(ESC_ALIGN_LEFT);
            outputStream.write(msg.getBytes());
            outputStream.write(ESC_ALIGN_CENTER);
            msg = "------------------------------";
            msg += "\r\n";
            msg += "\r\nTOTAL:  $"+total + "\r\n";
            msg += "\r\n";
            outputStream.write(msg.getBytes());
            msg = "";
            msg += "\r\n";
            msg += "\r\nMuchas Gracias por tu Compra\r\n";
            msg += "\r\n";
            msg += "\r\n";
            msg += "\r\n";
            outputStream.write(ESC_ALIGN_CENTER);
            outputStream.write(msg.getBytes());
            disconnectBT();
        }catch (Exception ex){
            ex.printStackTrace();
            disconnectBT();
            Toast.makeText(context,"Aqui estoy donde es"+ex,Toast.LENGTH_LONG).show();
        }
    }

    //Este método fue usado con la impresora genérica anterior y funcionaba bien
    /*public void printData(ArrayList<DetallePedido> arrayList,String cliente, int idCliente, String total) throws  IOException{
        try{
            Toast.makeText(context,"Imprimiendo.",Toast.LENGTH_LONG).show();
            //printPhoto(R.drawable.logoticket);
            String msg = "Arbolito";
            msg += "\n";
            msg += "Fecha: "+fecha+"  \n";
            msg+= "Arandas, Jalisco";
            msg += "\n";
            msg += "------------------------------";
            msg += "\n";
            msg += "Cliente: " + cliente + " #" + idCliente;
            outputStream.write(ESC_ALIGN_CENTER);
            outputStream.write(msg.getBytes());
            msg = ""+cliente+" \n";
            msg += "------------------------------";
            msg += "\n";
            msg += "CANT.   DESCRIPCION   IMP   ";
            msg += "\n";
            msg += "------------------------------";
            outputStream.write(msg.getBytes());
            msg = "\n\n";
            for(int i=0; i < arrayList.size(); i++){
                if(arrayList.get(i).getCant() != 0) {
                    msg += "" + arrayList.get(i).getCant() + "   " + arrayList.get(i).getDesc() + " $" + arrayList.get(i).getImp() + ".00  ";
                    msg += "\n";
                    msg += "\n";
                }
            }
            msg += "\n";
            outputStream.write(ESC_ALIGN_LEFT);
            outputStream.write(msg.getBytes());
            outputStream.write(ESC_ALIGN_CENTER);
            msg = "------------------------------";
            msg += "\n";
            msg += "TOTAL:  $"+total;
            msg += "\n";
            outputStream.write(msg.getBytes());
            msg = "";
            msg += "\n";
            msg += "Muchas Gracias por tu Compra";
            msg += "\n";
            msg += "\n";
            msg += "\n";
            outputStream.write(ESC_ALIGN_CENTER);
            outputStream.write(msg.getBytes());
            disconnectBT();
        }catch (Exception ex){
            ex.printStackTrace();
            disconnectBT();
            Toast.makeText(context,"Aqui estoy donde es"+ex,Toast.LENGTH_LONG).show();
        }
    }*/



    // Disconnect Printer //
    public void disconnectBT() throws IOException{
        try {
            stopWorker=true;
            outputStream.close();
            inputStream.close();
            bluetoothSocket.close();
            //lblPrinterName.setText("Printer Disconnected.");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void printPhoto(int img) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(resources,
                    img);
            if(bmp!=null){
                byte[] command = Utils.decodeBitmap(bmp);
                outputStream.write(ESC_ALIGN_CENTER);
                outputStream.write(command);

            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
            Toast.makeText(context,"Ahora estoy aca en imagen"+e,Toast.LENGTH_LONG).show();
        }
    }

}
