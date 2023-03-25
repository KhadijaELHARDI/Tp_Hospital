package ma.emsi.hospital;

import ma.emsi.hospital.entities.Patient;
import ma.emsi.hospital.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

import static org.springframework.data.domain.PageRequest.*;

@SpringBootApplication
public class HospitalApplication implements CommandLineRunner {
   @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {

        SpringApplication.run(HospitalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i=0;i <100;i++) {
            patientRepository.save(
                    new Patient(null, "hassan", new Date(), Math.random()>0.5?true:false, (int)Math.random()*100));
        }//patientRepository.save(
                //new Patient(null,"mohamed",new Date(),true,100));
        //patientRepository.save(
                //new Patient(null,"imane",new Date(),false,210));
Page<Patient> patients=patientRepository.findAll(of(0,5));
        System.out.println("Total pages:"+patients.getTotalPages());
        System.out.println("Total elemnets:"+patients.getTotalElements());
        System.out.println("Num Page:"+patients.getNumber());
        List<Patient> content = patients.getContent();
        Page<Patient> byMalade =patientRepository.findByMalade(true,PageRequest.of(0,4));
        List<Patient> patientList=patientRepository.chercherPatients("%h%",40);
        byMalade.forEach(p->{
    System.out.println( "============================");
    System.out.println(p.getId());
    System.out.println(p.getNom());
    System.out.println(p.getScore());
    System.out.println(p.getDateNaissance());
    System.out.println(p.isMalade());
});
        System.out.println("*********************************");
        Patient patient=patientRepository.findById(1L).orElse(null);
        if(patient !=null){
            System.out.println(patient.getNom());
            System.out.println(patient.isMalade());
        }
        patient.setScore(870);
        patientRepository.save(patient);
        patientRepository.deleteById(1L);
     }
}
