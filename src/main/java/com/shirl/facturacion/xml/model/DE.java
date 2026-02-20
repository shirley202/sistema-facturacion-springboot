package com.shirl.facturacion.xml.model;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(
        name = "DE",
        namespace = "http://ekuatia.set.gov.py/sifen/xsd"
)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "gDatGralOpe",
        "gEmis",
        "gReceptor",
        "gTotSub"
})
public class DE {

    @XmlElement(name = "gDatGralOpe")
    private GDatosGenerales gDatGralOpe;

    @XmlElement(name = "gEmis")
    private GEmisor gEmis;

    @XmlElement(name = "gReceptor")
    private GReceptor gReceptor;

    @XmlElement(name = "gTotSub")
    private GTotales gTotSub;

    public DE() {
        // JAXB necesita constructor vacío
    }

    public GDatosGenerales getGDatGralOpe() {
        return gDatGralOpe;
    }

    public void setGDatGralOpe(GDatosGenerales gDatGralOpe) {
        this.gDatGralOpe = gDatGralOpe;
    }

    public GEmisor getGEmis() {
        return gEmis;
    }

    public void setGEmis(GEmisor gEmis) {
        this.gEmis = gEmis;
    }

    public GReceptor getGReceptor() {
        return gReceptor;
    }

    public void setGReceptor(GReceptor gReceptor) {
        this.gReceptor = gReceptor;
    }

    public GTotales getGTotSub() {
        return gTotSub;
    }

    public void setGTotSub(GTotales gTotSub) {
        this.gTotSub = gTotSub;
    }
}