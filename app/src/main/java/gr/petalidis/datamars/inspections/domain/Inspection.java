package gr.petalidis.datamars.inspections.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import gr.petalidis.datamars.inspections.dto.ThumbnailDto;
import gr.petalidis.datamars.rsglibrary.Rsg;

public class Inspection implements Serializable {
    private final static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    private UUID id = UUID.randomUUID();

    private Date date = new Date();

    private String producer1Tin = "";
    private String producer1Name = "";
    private String producer2Tin = "";
    private String producer2Name = "";
    private String producer3Tin = "";
    private String producer3Name = "";
    private String producer4Tin = "";
    private String producer4Name = "";

    private List<Entry> entries = new ArrayList<>();

    private List<ScannedDocument> scannedDocuments = new ArrayList<>();

    private double latitude = 0.0f;
    private double longitude = 0.0f;

    public Inspection(Date date, String producer1Tin, String producer1Name) {
        this.date = date;
        this.producer1Tin = producer1Tin;
        this.producer1Name = producer1Name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProducer1Tin() {
        return producer1Tin;
    }

    public void setProducer1Tin(String producer1Tin) {
        this.producer1Tin = producer1Tin;
    }

    public String getProducer1Name() {
        return producer1Name;
    }

    public void setProducer1Name(String producer1Name) {
        this.producer1Name = producer1Name;
    }

    public String getProducer2Tin() {
        return producer2Tin;
    }

    public void setProducer2Tin(String producer2Tin) {
        this.producer2Tin = producer2Tin;
    }

    public String getProducer2Name() {
        return producer2Name;
    }

    public void setProducer2Name(String producer2Name) {
        this.producer2Name = producer2Name;
    }

    public String getProducer3Tin() {
        return producer3Tin;
    }

    public void setProducer3Tin(String producer3Tin) {
        this.producer3Tin = producer3Tin;
    }

    public String getProducer3Name() {
        return producer3Name;
    }

    public void setProducer3Name(String producer3Name) {
        this.producer3Name = producer3Name;
    }

    public String getProducer4Tin() {
        return producer4Tin;
    }

    public void setProducer4Tin(String producer4Tin) {
        this.producer4Tin = producer4Tin;
    }

    public String getProducer4Name() {
        return producer4Name;
    }

    public void setProducer4Name(String producer4Name) {
        this.producer4Name = producer4Name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public List<Entry> getEntries() {
        return entries;
    }

    public List<ScannedDocument> getScannedDocuments() {
        return scannedDocuments;
    }

    public void setScannedDocuments(List<ScannedDocument> scannedDocuments) {
        this.scannedDocuments = scannedDocuments;
    }

    public void initScannedDocuments(List<ThumbnailDto> thumbnailDtos) {
        this.scannedDocuments = thumbnailDtos.stream().map(x-> new ScannedDocument(this.id,x.getImagePath())).collect(Collectors.toList());
    }
    public void initEntries(Set<Rsg> rsgs)
    {

        this.entries = rsgs
                .stream()
                .map(x->new Entry(this,
                        x.getCountryCode(),
                        x.getIdentificationCode(),
                        x.getDate())).collect(Collectors.toList());
    }
    public void associateEntries(String tagPart, String tin, String name)
    {

        this.entries.stream().filter(x->x.getTag().substring(4,8).equals(tagPart)).forEach(x->{
            x.setProducer(name);
            x.setProducerTin(tin);
        });
    }
    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Entry> getValidEntries()
    {
        return this.entries.stream().filter(x->!x.getProducer().isEmpty() && !x.getProducer().equals("Άλλος") ).collect(Collectors.toList());
    }

    public List<Inspectee> getProducers()
    {
        List<Inspectee> names = new ArrayList<>();
        names.add(new Inspectee(producer1Tin,producer1Name));
        if (!producer2Name.trim().isEmpty()) names.add(new Inspectee(producer2Tin,producer2Name));
        if (!producer3Name.trim().isEmpty()) names.add(new Inspectee(producer3Tin,producer3Name));
        if (!producer4Name.trim().isEmpty()) names.add(new Inspectee(producer4Tin,producer4Name));
        names.add(Inspectee.getDummyInspectee());
        return names;
    }

    public List<String> getProducerTins()
    {
        List<String> names = new ArrayList<>();
        names.add(producer1Tin);
        if (!producer2Tin.trim().isEmpty()) names.add(producer2Tin);
        if (!producer3Tin.trim().isEmpty()) names.add(producer3Tin);
        if (!producer4Tin.trim().isEmpty()) names.add(producer4Tin);
        names.add(Inspectee.getDummyInspectee().getName());
        return names;
    }

    public long getValidEntriesCount() {
        return getEntries().stream().filter(x->!x.isDummy()).count();
    }

    public long getInRegisterCount() {
        return getEntries().stream().filter(x->!x.isDummy() && x.isInRegister()==true).count();
    }

    public long getLambCount() {
        return getEntries().stream().filter(x->!x.isDummy() && x.isInRegister()==true
                && x.getAnimalType().trim().equals(Animals.LAMB_ANIMAL)).count();
    }
    public long getSheepCount(){
        return getEntries().stream().filter(x->!x.isDummy() && x.isInRegister()==true
                && x.getAnimalType().trim().equals(Animals.SHEEP_ANIMAL)).count();
    }
    public long getKidCount() {
        return getEntries().stream().filter(x->!x.isDummy() && x.isInRegister()==true
                && x.getAnimalType().trim().equals(Animals.KID_ANIMAL)).count();
    }
    public long getGoatCount() {
        return getEntries().stream().filter(x->!x.isDummy() && x.isInRegister()==true
                && x.getAnimalType().trim().equals(Animals.GOAT_ANIMAL)).count();
    }
    public long getRamCount() {
        return getEntries().stream().filter(x->!x.isDummy() && x.isInRegister()==true
                && x.getAnimalType().trim().equals(Animals.RAM_ANIMAL)).count();
    }
    public long getHeGoatCount() {
        return getEntries().stream().filter(x->!x.isDummy() && x.isInRegister()==true
                && x.getAnimalType().trim().equals(Animals.HEGOAT_ANIMAL)).count();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> toStrings()
    {
        List<String> strings = new ArrayList<>();
        String line0="Ημερομηνία:" + "," + format.format(date)+"\n";
        String line1="ΑΦΜ Παραγωγού:" +"," + producer1Tin +","+"Ονοματεπώνυμο:" + "," + producer1Name;
        String line2="ΑΦΜ 1ου συστεγαζόμενου:" +"," + producer2Tin +","+"Ονοματεπώνυμο:" + "," + producer2Name;
        String line3="ΑΦΜ 2ου συστεγαζόμενου:" +"," + producer3Tin +","+"Ονοματεπώνυμο:" + "," + producer3Name;
        String line4="ΑΦΜ 3ου συστεγαζόμενου:" +"," + producer4Tin +","+"Ονοματεπώνυμο:" + "," + producer4Name;

        String line5 = "Συντεταγμένες:" +"," +latitude + "," + longitude;
        strings.add(line0);
        strings.add(line1);
        strings.add(line2);
        strings.add(line3);
        strings.add(line4);
        strings.add(line4);
        strings.add(line5);
        String header = "Χώρα, Ενώτιο,Ημ/νια Ελέγχου,Βρέθηκε στο μητρώο,Ονοματεπώνυμο Παραγωγού,ΑΦΜ Παραγωγού,Είδος ζώου, Σχόλια";
        strings.add(header);
        strings.addAll(entries.stream().filter(x->!x.isDummy()).map(x->x.toString()).collect(Collectors.toList()));
        return strings;
    }
}
