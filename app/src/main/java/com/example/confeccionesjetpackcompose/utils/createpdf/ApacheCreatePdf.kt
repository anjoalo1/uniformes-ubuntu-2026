package com.example.confeccionesjetpackcompose.utils.createpdf
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi

import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPage

import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.font.PDType1Font
import java.io.File
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import com.example.confeccionesjetpackcompose.utils.dataclass.DataCustomer
import com.example.confeccionesjetpackcompose.utils.dataclass.DataHeaderCustomer
import com.example.confeccionesjetpackcompose.utils.dataclass.Uniformes
import com.tom_roush.pdfbox.pdmodel.graphics.color.PDColor
import com.tom_roush.pdfbox.pdmodel.graphics.color.PDDeviceRGB
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale






import java.io.IOException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


@RequiresApi(Build.VERSION_CODES.O)
class ApacheCreatePdf {


    val document = PDDocument()
    val page = PDPage()
    lateinit var contentStream: PDPageContentStream
    val myfecha:String


    // Usa 'val' si la variable de color no va a cambiar (es la opción preferida en Kotlin).
    val cellHeaderTables = PDColor(floatArrayOf(1f, 0.06f, 0.29f), PDDeviceRGB.INSTANCE)


    init {
        document.addPage(page)
        contentStream = PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)
        myfecha = obtenerFecha2()
    }


    val cellHeight = 20f

    @RequiresApi(Build.VERSION_CODES.O)
    fun headerPdfLocal(dataCustomer: DataCustomer){

        val font = PDType1Font.HELVETICA_BOLD
        contentStream.setFont(font, 12f)

        contentStream.setNonStrokingColor(0.9f, 0.4f, 0.6f) // verde
        contentStream.addRect(5f, 700f, 600f, 5f)
        contentStream.fill()
        contentStream.stroke()


        contentStream.setNonStrokingColor(0f, 0f, 0f)
        contentStream.beginText();
        contentStream.setFont(font, 24f)
        contentStream.setLeading(15f)
        //tx possicion en x, ty posicion en y
        contentStream.newLineAtOffset(60f, 760f)

        contentStream.showText("FACTURA DE VENTA")
        contentStream.endText()


        contentStream.setFont(font, 12f)
        //texto de color blanco
        contentStream.setNonStrokingColor(0f, 0f, 0f)
        contentStream.beginText();
        contentStream.setFont(font, 12f)
        contentStream.setLeading(15f)
        //tx possicion en x, ty posicion en y
        contentStream.newLineAtOffset(30f, 730f)
        val text1:String ="first text"
        contentStream.showText("FACTURA DE VENTA:  $myfecha")
        contentStream.endText()


        contentStream.beginText();
        contentStream.setFont(font, 12f)
        contentStream.setLeading(15f)
        //tx possicion en x, ty posicion en y
        contentStream.newLineAtOffset(30f, 715f)
        contentStream.showText("CONFECCIONES MONADITA    CALLE 73 NORTE 17-55 POPAYÁN  312 246 9273 NIT - 36277024")
        contentStream.endText()


        /******************************/

        //val customer1 =(DataCustomer("Juan perez","123","456","46", "fasd@gmail.com"))
        val customer1 = dataCustomer;
        val datos3 = listOf(
            DataHeaderCustomer("Nombre:", customer1.name, "Identificación:", customer1.cardId),
            DataHeaderCustomer("Dirección:",customer1.address,"Teléfono:",customer1.cellNumber),
            DataHeaderCustomer("Email:",customer1.email,"","")
        )

        val columnWidths = listOf(90f, 240f, 90f, 150f) // Anchos personalizados para cada columna
        val tableWidth = columnWidths.sum() // Ancho total de la tabla
        val tableHeight = cellHeight * (datos3.size) // Encabezado + filas de datos

        val yPosition = 690f
        val xPosition =30f

        // Encabezados
        //val headers = listOf("Id", "Nombre", "Cantidad", "Precio Unit", "Total")
        //val numColumns = columnWidths.size

        datos3.forEachIndexed { rowIndex, item ->
            val currentYPosition = yPosition - (rowIndex + 1) * cellHeight
            val rowData = listOf(item.firstElement, item.firstElementVoid, item.secontElement, item.secondElementVoid)

            rowData.forEachIndexed { index, dato ->
                // Color de fondo para filas
                contentStream.setNonStrokingColor(0.9f, 0.9f, 0.9f) // Gris claro
                contentStream.addRect(xPosition + columnWidths.take(index).sum(), currentYPosition, columnWidths[index], cellHeight)
                contentStream.fill()

                // Dibujar borde de la celda de datos
                /*contentStream.setStrokingColor(0.2f, 0.2f, 0.2f) // Gris oscuro
                contentStream.addRect(xPosition + columnWidths.take(index).sum(), currentYPosition, columnWidths[index], cellHeight)
                contentStream.stroke()*/

                // Color del texto
                contentStream.setNonStrokingColor(0f / 255f) // Negro
                contentStream.beginText()
                contentStream.newLineAtOffset(xPosition + columnWidths.take(index).sum() + 5, currentYPosition + 5)
                contentStream.showText(dato)
                contentStream.endText()
            }
        }
        contentStream.fill()
        contentStream.stroke()

        //datos para fecha dia mes año
        //datos para fecha dia mes año
        //datos para fecha dia mes año


        val headerDate = listOf("Dia", "Mes", "Año")
        val columsDate = listOf(40f, 40f, 50f)
        val xDatePosition = 460f
        val yDatePosition = 760f
        //crear encabezado fecha "dia" "mes" "año"

        val dateShort = obtenerNewFecha2()
        val extractYerMothDay= dateShort.take(3)


        headerDate.forEachIndexed{index,  header->
            contentStream.setNonStrokingColor(cellHeaderTables)
            contentStream.addRect(xDatePosition + columsDate.take(index).sum(), yDatePosition, columsDate[index], cellHeight)
            contentStream.fill()
            // Dibujar borde de la celda del encabezado
            contentStream.setStrokingColor(0.2f, 0.2f, 0.2f) // Gris oscuro
            contentStream.addRect(xDatePosition + columsDate.take(index).sum(), yDatePosition, columsDate[index], cellHeight)
            contentStream.stroke()
            // Color del texto
            contentStream.setNonStrokingColor(0f / 255f) // Blanco
            contentStream.beginText()
            contentStream.newLineAtOffset(xDatePosition + columsDate.take(index).sum() + 5, yDatePosition + 5)
            contentStream.showText(header)
            contentStream.endText()
        }
        contentStream.fill()
        contentStream.stroke()

        //llenar datos dia mes año de la lista con el valor ejemplo 10-03-2025
        extractYerMothDay.forEachIndexed{index,  header->
            contentStream.setNonStrokingColor(0.9f, 0.9f, 0.9f) // Verde
            contentStream.addRect(xDatePosition + columsDate.take(index).sum(), yDatePosition-cellHeight, columsDate[index], cellHeight)
            contentStream.fill()
            // Dibujar borde de la celda del encabezado
            contentStream.setStrokingColor(0.2f, 0.2f, 0.2f) // Gris oscuro
            contentStream.addRect(xDatePosition + columsDate.take(index).sum(), yDatePosition-cellHeight, columsDate[index], cellHeight)
            contentStream.stroke()
            // Color del texto
            contentStream.setNonStrokingColor(0f / 255f) // Blanco
            contentStream.beginText()
            contentStream.newLineAtOffset(xDatePosition + columsDate.take(index).sum() + 5, yDatePosition-cellHeight + 5)
            contentStream.showText(header.toString())
            contentStream.endText()
        }
        contentStream.fill()
        contentStream.stroke()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun crearPDFConTabla(productos: List<Uniformes>): String {
        val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val horaColombia = ZonedDateTime.now(ZoneId.of("America/Bogota"))
        val horaFormateada = horaColombia.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"))
        val file = File(downloadDir, "$myfecha.pdf")

        /* val document = PDDocument()
         val page = PDPage()*/
        //document.addPage(page)

        //val contentStream = PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)

        val font = PDType1Font.HELVETICA_BOLD
        contentStream.setFont(font, 12f)

        // Insertar texto antes de la tabla

        // Configuraciones de tabla
        val xPosition = 50f
        val yPosition = 570f
        val cellHeight = 30f

        contentStream.setNonStrokingColor(0.3f, 0.03f, 0.77f) // Verde
        contentStream.addRect(50f, 800f, 490f, 30f)
        contentStream.fill()

        // Definir los anchos de cada columna
        val columnWidths = listOf( 120f, 100f, 70f, 100f, 100f) // Anchos personalizados para cada columna
        val tableWidth = columnWidths.sum() // Ancho total de la tabla
        val tableHeight = cellHeight * (productos.size) // Encabezado + filas de datos

        // Encabezados
        val headers = listOf( "Nombre","Talla", "Cantidad", "Precio", "Total")
        val numColumns = columnWidths.size


        // Dibujar encabezados con color y bordes
        headers.forEachIndexed { index, header ->
            // Color de fondo para encabezado
            //contentStream.setNonStrokingColor(0.36f, 0.28f, 0.36f) //
            contentStream.setNonStrokingColor(cellHeaderTables)
            contentStream.addRect(xPosition + columnWidths.take(index).sum(), yPosition, columnWidths[index], cellHeight)
            contentStream.fill()

            // Dibujar borde de la celda del encabezado
            /*contentStream.setStrokingColor(0.2f, 0.2f, 0.2f) // Gris oscuro
            contentStream.addRect(xPosition + columnWidths.take(index).sum(), yPosition, columnWidths[index], cellHeight)
            contentStream.stroke()*/

            // Color del texto
            contentStream.setNonStrokingColor(0f / 255f) // Blanco
            contentStream.beginText()
            contentStream.newLineAtOffset(xPosition + columnWidths.take(index).sum() + 5, yPosition + 10)
            contentStream.showText(header)
            contentStream.endText()
        }



        productos.forEachIndexed { rowIndex, item ->
            val currentYPosition = yPosition - (rowIndex + 1) * cellHeight
            val rowData = listOf(item.nameArticle, item.size, item.amount.toString(), item.price.toString(), item.total.toString())

            rowData.forEachIndexed { index, dato ->
                // Color de fondo para filas
                if(rowIndex % 2 == 0){
                    contentStream.setNonStrokingColor(0.9f, 0.9f, 0.9f) // Gris claro
                    contentStream.addRect(xPosition + columnWidths.take(index).sum(), currentYPosition, columnWidths[index], cellHeight)
                    contentStream.fill()
                }else{
                    contentStream.setNonStrokingColor(0.71f, 0.71f, 0.71f) // Gris claro
                    contentStream.addRect(xPosition + columnWidths.take(index).sum(), currentYPosition, columnWidths[index], cellHeight)
                    contentStream.fill()
                }

                // Dibujar borde de la celda de datos
                /*contentStream.setStrokingColor(0.2f, 0.2f, 0.2f) // Gris oscuro
                contentStream.addRect(xPosition + columnWidths.take(index).sum(), currentYPosition, columnWidths[index], cellHeight)
                contentStream.stroke()*/

                // Color del texto
                contentStream.setNonStrokingColor(0f / 255f) // Negro
                contentStream.beginText()
                contentStream.newLineAtOffset(xPosition + columnWidths.take(index).sum() + 5, currentYPosition + 10)
                contentStream.showText(dato)
                contentStream.endText()
            }
        }

        // Agregar la fila adicional con dos celdas combinadas

        // Bordes externos de la tabla (opcional si ya tienes bordes internos)
        /*contentStream.setStrokingColor(0.2f, 0.2f, 0.2f) // Gris oscuro para el borde externo
        contentStream.addRect(xPosition, yPosition - tableHeight, tableWidth, tableHeight)
        contentStream.stroke()*/

        val spaceTableFinal = 50f
        val yPositionTotalPagar =yPosition - ((cellHeight * (productos.size +1))+ spaceTableFinal)

        contentStream.setNonStrokingColor(1f, 0.06f, 0.29f)
        contentStream.addRect(300f, yPositionTotalPagar, 260f, cellHeight); // define los 4 lados del rectángulo
        contentStream.fill()


        val textTotalSum = numberFormated(productos.sumOf { it.total })
        contentStream.setNonStrokingColor(0f, 0f, 0f)
        contentStream.beginText()
        contentStream.newLineAtOffset(300f + 5, yPositionTotalPagar+5)
        contentStream.showText("TOTAL A PAGAR: $ $textTotalSum")
        contentStream.endText()




        /*val dateShort = obtenerNewFecha2()
        val ultimoscuatro = dateShort.takeLast(4)
        val concatenado = ultimoscuatro.joinToString(separator = "") // "1020305000" como String
        val numeroEntero = concatenado.toLong()

        contentStream.setNonStrokingColor(0f, 0f, 0f)
        contentStream.beginText()
        contentStream.newLineAtOffset( 10f, yPositionTotalPagar+5)
        contentStream.showText("TOTAL A PAGAR: $numeroEntero ")
        contentStream.endText()
*/



        contentStream.close()
        document.save(file)
        document.close()
        return myfecha
    }

}




@RequiresApi(Build.VERSION_CODES.O)
fun obtenerFecha2(): String{
    val horaColombia = ZonedDateTime.now(ZoneId.of("America/Bogota"))
    //val horaFormateada = horaColombia.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"))
    val horaFormateada = horaColombia.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss.SSS"))
    //System.out.println("hora formateada desde $horaFormateada")
    return horaFormateada
}



@RequiresApi(Build.VERSION_CODES.O)
fun obtenerNewFecha2():List<Any>{
    val horaColombia = ZonedDateTime.now(ZoneId.of("America/Bogota"))
    //val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss.SSSSSS")
    //val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSSXXX")
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS", Locale.forLanguageTag("es-ES"))
    val fechaFormateada = horaColombia.format(formatter)

    //println("Fecha formateada como string: $fechaFormateada")
    //print("\nDe fecha iso string a formato  $fechaFormateada")


    //val fechaIso = "2025-03-09T21:51:41.208"
    val mothSpanish = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
    val formatter2 = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val ldtFormatoIso = LocalDateTime.parse(fechaFormateada, formatter2)
    val ldtString = ldtFormatoIso.toString();
    //println("\n-fecha formato iso: $ldtFormatoIso")
    val anio = ldtFormatoIso.year
    val mes = ldtFormatoIso.monthValue
    val dia = ldtFormatoIso.dayOfMonth
    val nano = ldtFormatoIso.nano
    val hora = ldtFormatoIso.hour
    val minute = ldtFormatoIso.minute
    val segundo = ldtFormatoIso.second

    val mili = (nano/1000000).toInt()

    val mesTexto = mothSpanish[ldtFormatoIso.monthValue-1].uppercase()
    //println("\n year: $anio \n moth: $mes \n day: $dia \n mes: $mesTexto" )

    val myList = listOf(dia.toString(), mes.toString(), anio.toString(), ldtFormatoIso.toString(), fechaFormateada.toString(), dia.toString(), hora.toString(), minute.toString(), segundo.toString(), mili.toString())
    //return horaColombia.toString()
    return myList

}



fun numberFormated(numerToFormated: Double): String{
    //val numero = numerToFormated
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }
    val formato = DecimalFormat("#,##0.00", symbols)
    val numeroFormateado = formato.format(numerToFormated)
    //println(numeroFormateado)  // Salida: 20.000
    return numeroFormateado;
}