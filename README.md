# eBox
#tamaño pantalla: 1387x640
#barra inicial 58(ancho)
@darkPrimaryColor:   #388E3C; 56,142,60
@primaryColor:       #4CAF50; 76,175,80
@lightPrimaryColor:  #C8E6C9; 200,230,201
@textPrimaryColor:   #FFFFFF; 255,255,255
@accentColor:        #795548; 121,85,72
@primaryTextColor:   #212121; 33,33,33
@secondaryTextColor: #757575; 117,117,117
@dividerColor:       #BDBDBD; 189,189,189

#para compilar el codigo y asi obtener el jar con dependencias (se debe ejecutar en la carpeta donde se encuentre el POM)
mvn clean compile assembly:single

#version de java:
1.8.0_144;
#version de netbeans:
8.2 Build 201609300101
#SO
 Windows 10 version 10.0 running on amd64
