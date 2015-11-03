
package com.example.khue.movieinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Dates {

    @SerializedName("minimum")
    @Expose
    private String minimum;
    @SerializedName("maximum")
    @Expose
    private String maximum;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Dates() {
    }

    /**
     * 
     * @param minimum
     * @param maximum
     */
    public Dates(String minimum, String maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    /**
     * 
     * @return
     *     The minimum
     */
    public String getMinimum() {
        return minimum;
    }

    /**
     * 
     * @param minimum
     *     The minimum
     */
    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    /**
     * 
     * @return
     *     The maximum
     */
    public String getMaximum() {
        return maximum;
    }

    /**
     * 
     * @param maximum
     *     The maximum
     */
    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

}
