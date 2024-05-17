/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package devicestore;

/**
 *
 * @author 20100
 */
public class LaptopData {

    private Integer id;
    private String Lap_id;
    private String Type;
    private String Lap_Name;
    private String Display;
    private String CPU;
    private String GPU;
    private String Ram;
    private String Storage;
    private Double Price;
    private Integer quantity;
    private String image;

    public LaptopData(Integer id, String Lap_id, String Type, String Lap_Name,
            String Display, String CPU, String GPU, String Ram,
            String Storage, Double Price, String image) {
        this.id = id;
        this.Lap_id = Lap_id;
        this.Type = Type;
        this.Lap_Name = Lap_Name;
        this.Display = Display;
        this.CPU = CPU;
        this.GPU = GPU;
        this.Ram = Ram;
        this.Storage = Storage;
        this.Price = Price;
        this.image = image;
    }

    public LaptopData(Integer id, String Lap_id, String Lap_Name, String Type, Double Price, String image) {
        this.id = id;
        this.Lap_id = Lap_id;
        this.Type = Type;
        this.Lap_Name = Lap_Name;
        this.Price = Price;
        this.image = image;
    }

    public LaptopData(Integer id, String Lap_id, String Lap_Name, Integer quantity, String Type, Double Price, String image) {
        this.id = id;
        this.Lap_Name = Lap_Name;
        this.Lap_id = Lap_id;
        this.Type = Type;
        this.Price = Price;
        this.image = image;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public String getLap_id() {
        return Lap_id;
    }

    public String getType() {
        return Type;
    }

    public String getLap_Name() {
        return Lap_Name;
    }

    public String getDisplay() {
        return Display;
    }

    public String getCPU() {
        return CPU;
    }

    public String getGPU() {
        return GPU;
    }

    public String getRam() {
        return Ram;
    }

    public String getStorage() {
        return Storage;
    }

    public Double getPrice() {
        return Price;
    }

    public String getImage() {
        return image;
    }

    public Integer getQuantity() {
        return quantity;
    }

}
