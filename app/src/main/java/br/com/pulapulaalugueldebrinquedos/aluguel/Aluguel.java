package br.com.pulapulaalugueldebrinquedos.aluguel;

public class Aluguel {

    private int id;
    private int id_brinquedo;
    private int id_cliente;
    private String dataInicio;
    private String dataFim;
    //private Double total;

    public Aluguel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_brinquedo() {
        return id_brinquedo;
    }

    public void setId_brinquedo(int id_brinquedo) {
        this.id_brinquedo = id_brinquedo;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }
//
//    public Double getTotal() {
//        return total;
//    }
//
//    public void setTotal(Double total) {
//        this.total = total;
//    }
}
