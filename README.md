ToolBox
=======

### Version
0.0.4

Para instalar hay que agregar estas lineas al gradle de la app
```Gradle
repositories {
    maven {
        url 'https://dl.bintray.com/jojojr/maven/'
    }
    maven {
    url 'https://oss.sonatype.org/content/repositories/ksoap2-android-releases/'
    } }

...

dependencies {
    compile 'gmontenegro:toolboxlib:0.0.4'
}
```

SettingsManager
---------------

En la carpeta de Assets de la applicación hay que agregar un archivo statAsset que contenga un JSon
donde los siguientes parametros son obligatorios
```Json
{
  "debugTAG": "APPLICATIONTAG",
  "debug": "true",
  "saveLog": "true",
  "debugLevel": 4,
  "debugFileSize": 64000,
  "token": "TY8x6y7u4vNpqWoN",
  "defaultNamesapce": "http://www.example.org/sample/",
  ...
 }
```

Si se requieren mas parametros por default deben agregarse al archivo y sobrecargar la clase
DefaultSettings con las variables de los nuevos parametros.
Y sobrecargar el metodo

EncryptionManager
-----------------

Para mejorar la seguridad de la encripación, el EncryptionManager cuenta con dos tipos distitos de
passwords, uno general y uno por sesion.
El menos seguro pero mas facil de configurar es usar el password que se almacena en stateAsset bajo el nombre de "token".
El mas seguro es generar un password por sesión.
Al iniciar la sesión se debe llamara a:
```Java
EncryptionManager.initSessionToken();
```

Si se necesita almacenar un dato de sesion, al llamar a encrypt y decrypt hay que enviarle el parametro isForSession en true

```Java
EncryptionManager.encrypt(Mensaje,true);
...
EncryptionManager.decrypt(TextoCifrado,true);
```

Si un dato se encripta con un token, debe ser desencriptado con la mismo token, para recuperar su valor.
Como el token de sesión se pierde al cerrar la app, los datos guardados durante la sesión no se van a poder desencriptar

LogManager
----------
Para usar LogManager hay que invocar a los metodos estaticos que contiene en lugar de los estandares.
Ejemplo:
```Java
LogManager.debug(Text);
```
```Java
Log.d(Tag,Text);
```
Los parametros basicos de Log se definen el asset stateAsset.

```Json
{
  "debug": "true",
  "saveLog": "true",
  "debugLevel": 3,
  "debugFileSize": 4000,
  ...
```
**Debug**

 - true: encendido
 - false: apagado

**DebugLevel:**

 - 0: Sin log
 - 1: Solo Errores
 - 2: Errores y Warnings
 - 3: Verbose
 - 4:  Verbose + Toasts

**SaveLog**

 - true: Guarda un archivo con el log
 - false: No guarda el archivo

**DebugFileSize**
Es el tamaño en Bytes del archivo de log.

En el caso de querer mostrar en pantalla el log en tiempo real se puede usar el callback *OnLogChangedCallback* que avisa en cada cambio de log, pero con el cuidado de borrarlo antes de salir del activity.

SOAPWSManager
-------------

Para crear una invocación a un WS SOAP hay que crear una clase derivada de SOAPWSManager:

```Java
public class WSMock extends SoapWSManager  {

    public WSMock(OnWebServiceResponseCallback callback, String urlAmbiente, String methodName, LinkedHashMap paramsValues) {
        super(callback, urlAmbiente, methodName, paramsValues);
    }

    @Override
    protected Object parseObject(Object object) {
        return object;
    }


}
```
El metodo *parseObject* va a ser invocado con la respuesta del WS, respuesta que debemos formatear y luego será enviada al callback que se definio en el constructor.

Para crear el objeto encargado de llamar el WS solo hay que invocarlo de la siguiente forma:

```Java
...
new WSMock(this,
                "http://192.168.127.56:8088/mockSampleServiceSoapBinding",
                "login",
                SoapWSManager.createParameter("username","Login","password","Login123"));
...
```

El metodo createParameter es una forma de simplificar la creación del linkedHashMap que hay que enviarle al constructor.
La forma de invocarlo es escribiendo el nombre del campo seguido del objeto que va a ir en ese campo.

```Java
SoapWSManager.createParameter("name1",object1,"name2",object2,...));

```
