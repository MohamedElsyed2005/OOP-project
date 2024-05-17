/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package devicestore;

/**
 *
 * @author 20100
 */
public class CustomerData {
    private Integer id;
    private Integer CustomerID;
    private Double total;
    private String em_username;

    public CustomerData(Integer id, Integer CustomerID, Double total, String em_username) {
        this.id = id;
        this.CustomerID = CustomerID;
        this.total = total;
        this.em_username = em_username;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerID() {
        return CustomerID;
    }

    public Double getTotal() {
        return total;
    }

    public String getEm_username() {
        return em_username;
    }
    
    
    
}
