package archfx.com.epidermicare.app;

/**
 * Created by cybx_live on 12/17/17.
 */

public class User {

    private String id,name,email,datOfBirth,gender,nic,district,guardiansNic;
    private boolean isdoctor=false;
    private String doctorId;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDatOfBirth() {
        return datOfBirth;
    }

    public void setDatOfBirth(String datOfBirth) {
        this.datOfBirth = datOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGuardiansNic() {
        return guardiansNic;
    }

    public void setGuardiansNic(String guardiansNic) {
        this.guardiansNic = guardiansNic;
    }

    public boolean isIsdoctor() {
        return isdoctor;
    }

    public void setIsdoctor(boolean isdoctor) {
        this.isdoctor = isdoctor;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
