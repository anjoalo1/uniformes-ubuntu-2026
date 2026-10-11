package com.example.confeccionesjetpackcompose.utils.createpdf

import android.os.Environment
import com.example.confeccionesjetpackcompose.utils.CalculaFechas
import com.example.confeccionesjetpackcompose.utils.dataclass.DataCustomer
import com.example.confeccionesjetpackcompose.utils.dataclass.DataHeaderCustomer
import com.example.confeccionesjetpackcompose.utils.dataclass.DataHeaderCustomer2
import com.example.confeccionesjetpackcompose.utils.dataclass.Uniformes
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPage
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.common.PDRectangle
import com.tom_roush.pdfbox.pdmodel.font.PDType1Font
import com.tom_roush.pdfbox.pdmodel.graphics.color.PDColor
import com.tom_roush.pdfbox.pdmodel.graphics.color.PDDeviceRGB
import java.io.File
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ApacheCreatePdf58mm (val widthMM: Float, val heightMM: Float){

    // Constante de conversión de Milímetros a Puntos (1 mm ≈ 2.83464567 puntos)
    private val POINTS_PER_MM = 72f / 25.4f

    // Convierte las dimensiones a Puntos (float)
    private val widthPoints = widthMM * POINTS_PER_MM
    private val heightPoints = heightMM * POINTS_PER_MM

    // 1. Crea el objeto PDRectangle con las dimensiones personalizadas
    private val pageSize = PDRectangle(widthPoints, heightPoints)

    val document = PDDocument()
    lateinit var contentStream: PDPageContentStream
    val myfecha: String

    val cellHeaderTables = PDColor(floatArrayOf(1f, 0.06f, 0.29f), PDDeviceRGB.INSTANCE)

    // 2. Crea la PDPage usando el constructor que acepta un PDRectangle
    private val page = PDPage(pageSize)

    // Propiedades útiles de la página (disponibles después de crear page)
    val pageWidth: Float get() = page.mediaBox.width
    val pageHeight: Float get() = page.mediaBox.height

    init {
        // 3. Agrega la página al documento
        document.addPage(page)
        contentStream = PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)
        myfecha = obtenerFecha2()
    }


    fun headerPdfLocal(dataCustomer: DataCustomer, productos: List<Uniformes>) {


        val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val horaColombia = ZonedDateTime.now(ZoneId.of("America/Bogota"))
        //val horaFormateada = horaColombia.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"))
        val file = File(downloadDir, "58mm$myfecha.pdf")

        val dateFile = CalculaFechas().obtenerNewFecha2()
        val showDateHeaderBill:String = "Fecha: ${dateFile[0]}/${dateFile[1]}/${dateFile[2]}"
        val showHourHeaerBill:String = "Hora:  ${dateFile[6]}:${dateFile[7]}:${dateFile[8]}"
        val spaceDateAndHour:String = "     "
        val dateAndHour:String = "${showDateHeaderBill} ${spaceDateAndHour} ${showHourHeaerBill}"

        val font = PDType1Font.TIMES_ROMAN
        contentStream.setFont(font, 12f)


        contentStream.setNonStrokingColor(0f, 0f, 0f)
        contentStream.beginText();
        contentStream.setFont(font, 7f)
        contentStream.setLeading(15f)
        //tx possicion en x, ty posicion en y
        contentStream.newLineAtOffset(5f, pageHeight-10f)
        contentStream.showText("FACTURA DE VENTA")
        contentStream.endText()

        contentStream.setNonStrokingColor(0f, 0f, 0f)
        contentStream.beginText();
        contentStream.setFont(font, 7f)
        contentStream.setLeading(15f)
        //tx possicion en x, ty posicion en y
        contentStream.newLineAtOffset(5f, pageHeight-20f)
        contentStream.showText("CONFECCIONES MONADITA --- NIT: 36277024")
        contentStream.endText()

        contentStream.setNonStrokingColor(0f, 0f, 0f)
        contentStream.beginText();
        contentStream.setFont(font, 7f)
        contentStream.setLeading(15f)
        //tx possicion en x, ty posicion en y
        contentStream.newLineAtOffset(5f, pageHeight-30f)
        contentStream.showText("312 246 9273 CALLE 73 N 17 - 55. LA PRIMAVERA")
        contentStream.endText()


        contentStream.setNonStrokingColor(0f, 0f, 0f)
        contentStream.beginText();
        contentStream.setFont(font, 7f)
        contentStream.setLeading(15f)
        //tx possicion en x, ty posicion en y
        contentStream.newLineAtOffset(5f, pageHeight-40f)

        contentStream.showText(dateAndHour)
        contentStream.endText()

        val customer1 = dataCustomer;

        val columnWidthsCustomer = listOf(35f, 60f) // Anchos personalizados para cada columna
        val yPositionCustomer = pageHeight-50f
        val xPositionCustomer =5f
        val cellHeight = 9f
        val deleteEmptyDataCustomer = loadDataCustomerFinal(customer1)

        deleteEmptyDataCustomer.forEachIndexed { rowIndex, item ->
            val currentYPosition = yPositionCustomer - (rowIndex + 1) * cellHeight
            val rowData = listOf(item.firstElement, item.secondElement)


                rowData.forEachIndexed { index, dato ->
                    // Color de fondo para filas
                    /*contentStream.setNonStrokingColor(0.9f, 0.9f, 0.9f) // Gris claro
                    contentStream.addRect(xPosition + columnWidths.take(index).sum(), currentYPosition, columnWidths[index], cellHeight)
                    contentStream.fill()*/

                    // Color del texto
                    contentStream.setNonStrokingColor(0f / 255f) // Negro
                    contentStream.beginText()
                    contentStream.newLineAtOffset(xPositionCustomer + columnWidthsCustomer.take(index).sum() + 5, currentYPosition + 5)
                    contentStream.showText(dato)
                    contentStream.endText()

                }


        }
        contentStream.fill()
        contentStream.stroke()



        //productos
        val xPosition = 3f
        val yPosition =(yPositionCustomer) - ((deleteEmptyDataCustomer.size * cellHeight)+20f)
       // val yPosition = 140f
        // Encabezados
        val headers = listOf( "Nombre","Talla", "Cant", "Total")
        val columnWidths2 = listOf( 45f, 30f, 30f, 50f)

        headers.forEachIndexed { index, header ->

            // Color del texto
            contentStream.setNonStrokingColor(0f / 255f) // Blanco
            contentStream.beginText()
            contentStream.newLineAtOffset(xPosition + columnWidths2.take(index).sum() + 5, yPosition + 10)
            contentStream.showText(header)
            contentStream.endText()
        }


        productos.forEachIndexed { rowIndex, item ->
            val currentYPosition = yPosition - (rowIndex + 1) * cellHeight
            val rowData = listOf(item.nameArticle, item.size, item.amount.toString(), item.total.toString())

            rowData.forEachIndexed { index, dato ->
                // Color de fondo para filas
                /*if(rowIndex % 2 == 0){
                    contentStream.setNonStrokingColor(0.9f, 0.9f, 0.9f) // Gris claro
                    contentStream.addRect(xPosition + columnWidths2.take(index).sum(), currentYPosition, columnWidths2[index], cellHeight)
                    contentStream.fill()
                }else{
                    contentStream.setNonStrokingColor(0.71f, 0.71f, 0.71f) // Gris claro
                    contentStream.addRect(xPosition + columnWidths2.take(index).sum(), currentYPosition, columnWidths2[index], cellHeight)
                    contentStream.fill()
                }*/

                // Color del texto
                contentStream.setNonStrokingColor(0f / 255f) // Negro
                contentStream.beginText()
                contentStream.newLineAtOffset(xPosition + columnWidths2.take(index).sum() + 5, currentYPosition + 10)
                contentStream.showText(dato)
                contentStream.endText()
            }

        }



        val spaceTableFinal = 4f
        val yPositionTotalPagar =yPosition - ((cellHeight * (productos.size +1))+ spaceTableFinal)

        /*contentStream.setNonStrokingColor(1f, 0.06f, 0.29f)
        contentStream.addRect(5f, yPositionTotalPagar, 120f, cellHeight); // define los 4 lados del rectángulo
        contentStream.fill()*/


        val textTotalSum = numberFormated(productos.sumOf { it.total })

        contentStream.setNonStrokingColor(0f, 0f, 0f)
        contentStream.beginText()
        contentStream.setFont(font, 8f)
        contentStream.newLineAtOffset(5f + 5, yPositionTotalPagar+2)
        contentStream.showText("TOTAL A PAGAR: $ $textTotalSum")
        contentStream.endText()

        contentStream.fill()
        contentStream.stroke()




        contentStream.close()
        document.save(file)
        document.close()
    }

// ... (Aquí agregarías contenido)

// document.save("documento_58mm.pdf")
// document.close()
}

fun loadDataCustomerFinal(dataCustomer: DataCustomer):List<DataHeaderCustomer2>{
    val datos4 = listOf(
        DataHeaderCustomer2("Nombre: ", dataCustomer.name),
        DataHeaderCustomer2("Ident: ", dataCustomer.cardId),
        DataHeaderCustomer2("Dirección : ", dataCustomer.address),
        DataHeaderCustomer2("Teléfono: ", dataCustomer.cellNumber),
        DataHeaderCustomer2("Email : ", dataCustomer.email),
    )
    var listaCustomerFinal = mutableListOf<DataHeaderCustomer2>()

    for(persona in datos4 ){
        if(persona.secondElement.isBlank()){

        }else{
            val customerTemporal = DataHeaderCustomer2(persona.firstElement, persona.secondElement)
            listaCustomerFinal.add(customerTemporal)
        }
    }
    return listaCustomerFinal
}