ToolBox
=======
### Version
0.0.3

Para instalar hay que agregar estas lineas al gradle de la app
```Gradle
repositories {
    maven {
        url 'https://dl.bintray.com/jojojr/maven/'
    }
    maven {
    url 'https://oss.sonatype.org/content/repositories/ksoap2-android-releases/'
    }
  }

...

dependencies {
    compile 'gmontenegro:toolboxlib:0.0.1'
}
```

SettingsManager
---------------

En la carpeta de Assets de la applicación hay que agregar un archivo statAsset que contenga un JSon
donde los siguientes parametros son obligatorios
```Json
{
	"debug": "true",
	"saveLog": "true",
	"debugLevel": 3,
	"token" : "Token"
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

