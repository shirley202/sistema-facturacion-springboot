package com.shirl.facturacion.xml;

import com.shirl.facturacion.model.Factura;
import com.shirl.facturacion.xml.model.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.time.format.DateTimeFormatter;



@Service
public class FacturaXmlService {

    public String generarXml(Factura factura) {

        try {

            DE de = new DE();

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            de.setGDatGralOpe(
                    new GDatosGenerales(
                            factura.getFechaEmision().format(formatter)
                    )
            );

            de.setGEmis(
                    new GEmisor("80012345", "EMPRESA DEMO S.A.")
            );

            de.setGReceptor(
                    new GReceptor(
                            factura.getCliente().getNombre(),
                            factura.getCliente().getNumeroDocumento()
                    )
            );

            de.setGTotSub(
                    new GTotales(
                            factura.getTotalGeneral(),
                            factura.getTotalIva()
                    )
            );
            JAXBContext context = JAXBContext.newInstance(DE.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            StringWriter writer = new StringWriter();
            marshaller.marshal(de, writer);

            return writer.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error generando XML", e);
        }


    }

}