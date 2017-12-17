package archfx.com.epidermicare.app;

/**
 * Created by cybx_live on 12/17/17.
 */

public class Report
{

    String diseaseName,Area,ReportedDate,PatientName,PatientNic;
    int status;
    Report(String diseaseName,String Area,String ReportedDate,String PatientName,String PatientNic,int status)
    {
        this.Area=Area;
        this.diseaseName=diseaseName;
        this.ReportedDate=ReportedDate;
        this.PatientName=PatientName;
        this.PatientNic=PatientNic;
        this.status=status;
    }

    public int getStatus() {
        return status;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public String getArea() {
        return Area;
    }

    public String getReportedDate() {
        return ReportedDate;
    }

    public String getPatientName() {
        return PatientName;
    }

    public String getPatientNic() {
        return PatientNic;
    }
}
