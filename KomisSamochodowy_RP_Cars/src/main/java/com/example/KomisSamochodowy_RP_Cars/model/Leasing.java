package com.example.KomisSamochodowy_RP_Cars.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name="Leasing_Tabelka")
@Table(name="LEASING_TABELKA")
public class Leasing implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "egzemplarz_id")
    private Egzemplarz egzemplarz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "klient_id")
    private Klient klient;

    @Column(name="data_początek")
    private LocalDate data_poczatek;

    @Column(name="data_koniec")
    private LocalDate data_koniec;

    @Column(name="oplata_miesieczna")
    private double oplata_miesieczna;

    public Leasing(Egzemplarz egzemplarz, Klient klient,
                   LocalDate data_poczatek, LocalDate data_koniec,
                   double oplata_miesieczna) {
        this.egzemplarz = egzemplarz;
        this.klient = klient;
        this.data_poczatek = data_poczatek;
        this.data_koniec = data_koniec;
        this.oplata_miesieczna = oplata_miesieczna;
    }

    public Leasing() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Egzemplarz getEgzemplarz() {
        return egzemplarz;
    }

    public void setEgzemplarz(Egzemplarz egzemplarz) {
        this.egzemplarz = egzemplarz;
    }

    public Klient getKlient() {
        return klient;
    }

    public void setKlient(Klient klient) {
        this.klient = klient;
    }

    public LocalDate getData_poczatek() {
        return data_poczatek;
    }

    public void setData_poczatek(LocalDate data_poczatek) {
        this.data_poczatek = data_poczatek;
    }

    public LocalDate getData_koniec() {
        return data_koniec;
    }

    public void setData_koniec(LocalDate data_koniec) {
        this.data_koniec = data_koniec;
    }

    public double getOplata_miesieczna() {
        return oplata_miesieczna;
    }

    public void setOplata_miesieczna(double oplata_miesieczna) {
        this.oplata_miesieczna = oplata_miesieczna;
    }
}