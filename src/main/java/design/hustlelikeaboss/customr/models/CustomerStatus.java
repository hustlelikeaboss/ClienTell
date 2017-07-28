package design.hustlelikeaboss.customr.models;

/**
 * Created by quanjin on 7/26/17.
 */
public enum CustomerStatus {

    LEAD("Lead"), CONVERTED("Converted"), LOST("Lost");

    private String status;

    CustomerStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
