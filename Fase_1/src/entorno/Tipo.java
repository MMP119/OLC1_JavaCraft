package entorno;

import instruccion.TipoInstruccion;;

public class Tipo {
    private TipoInstruccion tipo;

    public Tipo(TipoInstruccion tipo) {
        this.tipo = tipo;
    }

    public TipoInstruccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoInstruccion tipo) {
        this.tipo = tipo;
    }

}
