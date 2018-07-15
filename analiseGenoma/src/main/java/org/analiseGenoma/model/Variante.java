package org.analiseGenoma.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToOne;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "variante")

@NamedStoredProcedureQuery(
        name = "insert_variant3", // name of stored procedure in the persistence unit
        procedureName = "insert_variant4", //name of  stored procedure in the database
        parameters
        = //Parameters of the stored procedure
        {
            @StoredProcedureParameter(// A parameter,
                    name = "allelicdeph1", //Name of the parameter
                    mode = ParameterMode.IN, // Mode of the parameter
                    type = Integer.class)
            , // JDBC Type.	
                        
        @StoredProcedureParameter(// A parameter,
                    name = "allelicdeph2", //Name of the parameter
                    mode = ParameterMode.IN, // Mode of the parameter
                    type = Integer.class)
            , // JDBC Type.

        @StoredProcedureParameter(// A parameter,
                    name = "geneSymbol", //Name of the parameter
                    mode = ParameterMode.IN, // Mode of the parameter
                    type = String.class), // JDBC Type.
                
        @StoredProcedureParameter(// A parameter,
                    name = "cromossomo", //Name of the parameter
                    mode = ParameterMode.IN, // Mode of the parameter
                    type = String.class), // JDBC Type.
        @StoredProcedureParameter(// A parameter,
                    name = "referencia", //Name of the parameter
                    mode = ParameterMode.IN, // Mode of the parameter
                    type = String.class), // JDBC Type.
        @StoredProcedureParameter( name = "alterado",       mode = ParameterMode.IN,    type = String.class), 
        @StoredProcedureParameter( name = "umdPredictor",   mode = ParameterMode.IN,    type = String.class),
        @StoredProcedureParameter( name = "zygosity",       mode = ParameterMode.IN,    type = String.class),
        @StoredProcedureParameter( name = "filter",         mode = ParameterMode.IN,    type = String.class),
        @StoredProcedureParameter( name = "hgvsC",          mode = ParameterMode.IN,    type = String.class),
        @StoredProcedureParameter( name = "hgvsP",          mode = ParameterMode.IN,    type = String.class),
        @StoredProcedureParameter( name = "idSnp",          mode = ParameterMode.IN,    type = String.class),
        @StoredProcedureParameter( name = "exonIntron",     mode = ParameterMode.IN,    type = Integer.class),
        
        }
)

//geneSymbol

public class Variante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_informacaovcf")
    private Long id;
    //@Column(columnDefinition = "text")

    //1
    @OneToOne
    @JoinColumn(name = "cromossomo_id")
    private Cromossomo cromossomo;
    //2
    //@Column(columnDefinition = "text")
    private Long position;
    //12
    @Column(columnDefinition = "text")
    private String idSNP;
    //3
    @Column(columnDefinition = "text")
    private String referencia;
    //3
    @Column(columnDefinition = "text")
    private String alterado;
    private Double qualidade;
    //16
    @ManyToOne
    @JoinColumn(name = "impacto_id")
    private Impact impact;
    //@Column(columnDefinition = "text")
    //private String filtro;
    //4
    @ManyToOne
    @JoinColumn(name = "gene_id")
    private Gene gene;
    @ManyToOne
    @JoinColumn(name = "vcf_id")
    private Vcf vcf;

    @ManyToOne(optional = true)
    @JoinColumn(name = "umdpredictor_id", nullable = true, insertable = true, updatable = true)
    private UmdPredictor umdPredictor;

    @ManyToOne
    @JoinColumn(name = "zygosity_id")
    private Zygosity zygosity;

    //private String allelicDeph;
    private int allelicDeph1;
    private int allelicDeph2;

    @ManyToOne
    @JoinColumn(name = "filter_id")
    private Filter filter;
    @Column(name = "hgvs_c")
    private String hgvsC;
    @Column(name = "hgvs_p")
    private String hgvsP;
    @Column(name = "exon_intron")
    private Integer exonIntron;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;
    @ManyToOne
    @JoinColumn(name = "effect_id")
    private Effect effect;
    @ManyToOne
    @JoinColumn(name = "clinvarsignificance_id")
    private ClinvarSignificance clinvarSignificance;
    @ManyToOne
    @JoinColumn(name = "clinvardisease_id")
    private ClinvarDisease clinvarDisease;
    @ManyToOne
    @JoinColumn(name = "clinvaraccession_id")
    private ClinvarAccession clinvarAccession;
    @ManyToOne
    @JoinColumn(name = "clinvaralleletype_id")
    private ClinvarAlleleType clinvarAlleleType;
    @ManyToOne
    @JoinColumn(name = "clinvaralleleorigin_id")
    private ClinvarAlleleOrigin clinvarAlleleOrigin;
    @ManyToOne
    @JoinColumn(name = "sift_id")
    private Sift sift;
    @ManyToOne
    @JoinColumn(name = "polyphenhdiv_id")
    private PolyphenHdiv polyphenHdiv;
    @ManyToOne
    @JoinColumn(name = "polyphenHvar_id")
    private PolyphenHvar polyphenHvar;
    @ManyToOne
    @JoinColumn(name = "mutationtaster_id")
    private MutationTaster mutationTaster;
    @ManyToOne
    @JoinColumn(name = "lrt_id")
    private Lrt lrt;
    private Double gerpRsScore;
    private Double gerpNeutralRate;
    @ManyToOne
    @JoinColumn(name = "feature_id")
    private Feature feature;
    @ManyToOne
    @JoinColumn(name = "_id")
    private Ensembl ensembl;
    private Double vertebrateGenomesConservationScore;
    @ManyToOne
    @JoinColumn(name = "interprodomain_id")
    private InterproDomain interproDomain;
    @ManyToOne
    @JoinColumn(name = "variantstatus_id")
    private VariantStatus variantStatus;
    @ManyToOne
    @JoinColumn(name = "genotype_id")
    private GenoType genoType;
    private String readDepth;
    private Double alleleMutFraction;
    private Double meanBaseQuality;
    private String varintType;
    private Boolean validate;
    private Boolean donorSpliceSite;
    private Boolean acceptorSpliceSite;
    private Boolean mutation;
    private Double europeanVarintFreq;
    private Double africanVarintFreq;
    private Double asianVarintFreq;
    private Double americanVarintFreq;
    private Double wholeVarintFreq;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cromossomo getCromossomo() {
        return cromossomo;
    }

    public void setCromossomo(Cromossomo cromossomo) {
        this.cromossomo = cromossomo;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(String s) {
        try {
            Long l = Long.valueOf(s);
            this.setPosition(l);
        } catch (Exception e) {
        }
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getIdSNP() {
        return idSNP;
    }

    public void setIdSNP(String idSNP) {
        if (("-").equals(idSNP)) {
            idSNP = null;
        }
        this.idSNP = idSNP;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getAlterado() {
        return alterado;
    }

    public void setAlterado(String alterado) {
        this.alterado = alterado;
    }

    public Double getQualidade() {
        return qualidade;
    }

    //@Transient
    public void setQualidade(String qualidade) {
        try {
            this.setQualidade(Double.valueOf(qualidade));
        } catch (NumberFormatException ex) {
            System.err.println("Erro ao definir qualidade da variate: " + ex + "qualidade: " + qualidade);
        }
    }

    public void setQualidade(Double qualidade) {
        this.qualidade = qualidade;
    }

    public Impact getImpact() {
        return impact;
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
    }

    public Gene getGene() {
        return gene;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    public Vcf getVcf() {
        return vcf;
    }

    public void setVcf(Vcf vcf) {
        this.vcf = vcf;
    }

    public UmdPredictor getUmdPredictor() {
        return umdPredictor;
    }

    public void setUmdPredictor(UmdPredictor umdPredictor) {
        this.umdPredictor = umdPredictor;
    }

    public Zygosity getZygosity() {
        return zygosity;
    }

    public void setZygosity(Zygosity zygosity) {
        this.zygosity = zygosity;
    }

    public int getAllelicDeph1() {
        return allelicDeph1;
    }

    public void setAllelicDeph1(int allelicDeph1) {
        this.allelicDeph1 = allelicDeph1;
    }

    public int getAllelicDeph2() {
        return allelicDeph2;
    }

    public void setAllelicDeph2(int allelicDeph2) {
        this.allelicDeph2 = allelicDeph2;
    }

    public String getAllelicDeph() {
        return allelicDeph1 + "/" + allelicDeph2;
    }

    public void setAllelicDeph(String allelicDeph) {
        //this.allelicDeph = allelicDeph;
        String[] all = allelicDeph.split("/");
        if (all.length == 2) {
            this.setAllelicDeph1(Integer.valueOf(all[0].trim()));
            this.setAllelicDeph2(Integer.valueOf(all[1].trim()));
        }
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public String getHgvsC() {
        return hgvsC;
    }

    public void setHgvsC(String hgvsC) {
        this.hgvsC = hgvsC;
    }

    public String getHgvsP() {
        return hgvsP;
    }

    public void setHgvsP(String hgvsP) {
        if ("-".equals(hgvsP)) {
            hgvsP = null;
        }
        this.hgvsP = hgvsP;
    }

    public Integer getExonIntron() {
        return exonIntron;
    }

    //@Transient
    public void setExonIntron(String exonIntron) {
        try {
            Integer n = Integer.valueOf(idSNP);
            this.setExonIntron(n);
        } catch (Exception ex) {
        }
    }

    public void setExonIntron(Integer exonIntron) {
        this.exonIntron = exonIntron;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        if (("-").equals(type.getName())) {
            type = null;
        }
        this.type = type;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public ClinvarSignificance getClinvarSignificance() {
        return clinvarSignificance;
    }

    public void setClinvarSignificance(ClinvarSignificance clinvarSignificance) {
        this.clinvarSignificance = clinvarSignificance;
    }

    public ClinvarDisease getClinvarDisease() {
        return clinvarDisease;
    }

    public void setClinvarDisease(ClinvarDisease clinvarDisease) {
        this.clinvarDisease = clinvarDisease;
    }

    public ClinvarAccession getClinvarAccession() {
        return clinvarAccession;
    }

    public void setClinvarAccession(ClinvarAccession clinvarAccession) {
        this.clinvarAccession = clinvarAccession;
    }

    public ClinvarAlleleType getClinvarAlleleType() {
        return clinvarAlleleType;
    }

    public void setClinvarAlleleType(ClinvarAlleleType clinvarAlleleType) {
        this.clinvarAlleleType = clinvarAlleleType;
    }

    public ClinvarAlleleOrigin getClinvarAlleleOrigin() {
        return clinvarAlleleOrigin;
    }

    public void setClinvarAlleleOrigin(ClinvarAlleleOrigin clinvarAlleleOrigin) {
        this.clinvarAlleleOrigin = clinvarAlleleOrigin;
    }

    public Sift getSift() {
        return sift;
    }

    public void setSift(Sift sift) {
        this.sift = sift;
    }

    public PolyphenHdiv getPolyphenHdiv() {
        return polyphenHdiv;
    }

    public void setPolyphenHdiv(PolyphenHdiv polyphenHdiv) {
        this.polyphenHdiv = polyphenHdiv;
    }

    public PolyphenHvar getPolyphenHvar() {
        return polyphenHvar;
    }

    public void setPolyphenHvar(PolyphenHvar polyphenHvar) {
        this.polyphenHvar = polyphenHvar;
    }

    public MutationTaster getMutationTaster() {
        return mutationTaster;
    }

    public void setMutationTaster(MutationTaster mutationTaster) {
        this.mutationTaster = mutationTaster;
    }

    public Lrt getLrt() {
        return lrt;
    }

    public void setLrt(Lrt lrt) {
        this.lrt = lrt;
    }

    public Double getGerpRsScore() {
        return gerpRsScore;
    }

    //@Transient
    public void setGerpRsScore(String gerpRsScore) {
        try {
            Double d = Double.valueOf(gerpRsScore);
            this.setGerpRsScore(d);
        } catch (Exception ex) {
        }
    }

    public void setGerpRsScore(Double gerpRsScore) {
        this.gerpRsScore = gerpRsScore;
    }

    public Double getGerpNeutralRate() {
        return gerpNeutralRate;
    }

    //@Transient
    public void setGerpNeutralRate(String gerpNeutralRate) {
        try {
            Double d = Double.valueOf(gerpNeutralRate);
            this.setGerpRsScore(d);
        } catch (Exception ex) {
        }
    }

    public void setGerpNeutralRate(Double gerpNeutralRate) {
        this.gerpNeutralRate = gerpNeutralRate;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public Ensembl getEnsembl() {
        return ensembl;
    }

    public void setEnsembl(Ensembl ensembl) {
        this.ensembl = ensembl;
    }

    public Double getVertebrateGenomesConservationScore() {
        return vertebrateGenomesConservationScore;
    }

    //@Transient
    public void setVertebrateGenomesConservationScore(String vertebrateGenomesConservationScore) {
        try {
            Double d = Double.valueOf(vertebrateGenomesConservationScore);
            this.setVertebrateGenomesConservationScore(d);
        } catch (Exception ex) {
        }
    }

    public void setVertebrateGenomesConservationScore(Double vertebrateGenomesConservationScore) {
        this.vertebrateGenomesConservationScore = vertebrateGenomesConservationScore;
    }

    public InterproDomain getInterproDomain() {
        return interproDomain;
    }

    public void setInterproDomain(InterproDomain interproDomain) {
        this.interproDomain = interproDomain;
    }

    public VariantStatus getVariantStatus() {
        return variantStatus;
    }

    public void setVariantStatus(VariantStatus variantStatus) {
        this.variantStatus = variantStatus;
    }

    public GenoType getGenoType() {
        return genoType;
    }

    public void setGenoType(GenoType genoType) {
        this.genoType = genoType;
    }

    public String getReadDepth() {
        return readDepth;
    }

    public void setReadDepth(String readDepth) {
        if (("-").equals(readDepth)) {
            readDepth = null;
        }
        this.readDepth = readDepth;
    }

    public Double getAlleleMutFraction() {
        return alleleMutFraction;
    }

    //@Transient
    public void setAlleleMutFraction(String alleleMutFraction) {
        try {
            Double d = Double.valueOf(alleleMutFraction);
            this.setAlleleMutFraction(d);
        } catch (Exception ex) {
        }
    }

    public void setAlleleMutFraction(Double alleleMutFraction) {
        this.alleleMutFraction = alleleMutFraction;
    }

    public Double getMeanBaseQuality() {
        return meanBaseQuality;
    }

    //@Transient
    public void setMeanBaseQuality(String meanBaseQuality) {
        try {
            Double d = Double.valueOf(meanBaseQuality);
            this.setMeanBaseQuality(d);
        } catch (Exception ex) {
        }
    }

    public void setMeanBaseQuality(Double meanBaseQuality) {
        this.meanBaseQuality = meanBaseQuality;
    }

    public Boolean getValidate() {
        return validate;
    }

    //@Transient
    public void setValidate(String validate) {
        if ("yes".equalsIgnoreCase(validate)) {
            this.setValidate(true);
        }
        if ("no".equalsIgnoreCase(validate)) {
            this.setValidate(false);
        }
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

    public Boolean getDonorSpliceSite() {
        return donorSpliceSite;
    }

    //@Transient
    public void setDonorSpliceSite(String valor) {
        if ("yes".equalsIgnoreCase(valor)) {
            this.setDonorSpliceSite(true);
        }
        if ("no".equalsIgnoreCase(valor)) {
            this.setDonorSpliceSite(false);
        }
    }

    public void setDonorSpliceSite(Boolean donorSpliceSite) {
        this.donorSpliceSite = donorSpliceSite;
    }

    public Boolean getAcceptorSpliceSite() {
        return acceptorSpliceSite;
    }

    //@Transient
    public void setAcceptorSpliceSite(String valor) {
        if ("yes".equalsIgnoreCase(valor)) {
            this.setAcceptorSpliceSite(true);
        }
        if ("no".equalsIgnoreCase(valor)) {
            this.setAcceptorSpliceSite(false);
        }
    }

    public void setAcceptorSpliceSite(Boolean acceptorSpliceSite) {
        this.acceptorSpliceSite = acceptorSpliceSite;
    }

    public Boolean getMutation() {
        return mutation;
    }

    //@Transient
    public void setMutation(String valor) {
        if ("yes".equalsIgnoreCase(valor)) {
            this.setMutation(true);
        }
        if ("no".equalsIgnoreCase(valor)) {
            this.setMutation(false);
        }
    }

    public void setMutation(Boolean mutation) {
        this.mutation = mutation;
    }

    public Double getEuropeanVarintFreq() {
        return europeanVarintFreq;
    }

    public void setEuropeanVarintFreq(Double europeanVarintFreq) {
        this.europeanVarintFreq = europeanVarintFreq;
    }

    public Double getAfricanVarintFreq() {
        return africanVarintFreq;
    }

    public void setAfricanVarintFreq(Double africanVarintFreq) {
        this.africanVarintFreq = africanVarintFreq;
    }

    public Double getAsianVarintFreq() {
        return asianVarintFreq;
    }

    public void setAsianVarintFreq(Double asianVarintFreq) {
        this.asianVarintFreq = asianVarintFreq;
    }

    public Double getAmericanVarintFreq() {
        return americanVarintFreq;
    }

    public void setAmericanVarintFreq(Double americanVarintFreq) {
        this.americanVarintFreq = americanVarintFreq;
    }

    public Double getWholeVarintFreq() {
        return wholeVarintFreq;
    }

    public void setWholeVarintFreq(Double wholeVarintFreq) {
        this.wholeVarintFreq = wholeVarintFreq;
    }

    public String getVarintType() {
        return varintType;
    }

    public void setVarintType(String varintType) {
        if (("-").equals(varintType)) {
            varintType = null;
        }
        this.varintType = varintType;
    }

    @Override
    public String toString() {
        return "Variante{" + "id=" + id + ", cromossomo=" + cromossomo + ", position=" + position + ", idSNP=" + idSNP + ", referencia=" + referencia + ", alterado=" + alterado + ", qualidade=" + qualidade + ", impact=" + impact + ", gene=" + gene + ", vcf=" + vcf + ", umdPredictor=" + umdPredictor + ", zygosity=" + zygosity + ", allelicDeph=" + getAllelicDeph() + ", filter=" + filter + ", hgvsC=" + hgvsC + ", hgvsP=" + hgvsP + ", exonIntron=" + exonIntron + ", type=" + type + ", effect=" + effect + ", clinvarSignificance=" + clinvarSignificance + ", clinvarDisease=" + clinvarDisease + ", clinvarAccession=" + clinvarAccession + ", clinvarAlleleType=" + clinvarAlleleType + ", clinvarAlleleOrigin=" + clinvarAlleleOrigin + ", sift=" + sift + ", polyphenHdiv=" + polyphenHdiv + ", polyphenHvar=" + polyphenHvar + ", mutationTaster=" + mutationTaster + ", lrt=" + lrt + ", gerpRsScore=" + gerpRsScore + ", gerpNeutralRate=" + gerpNeutralRate + ", feature=" + feature + ", ensembl=" + ensembl + ", vertebrateGenomesConservationScore=" + vertebrateGenomesConservationScore + ", interproDomain=" + interproDomain + ", variantStatus=" + variantStatus + ", genoType=" + genoType + ", readDepth=" + readDepth + ", alleleMutFraction=" + alleleMutFraction + ", meanBaseQuality=" + meanBaseQuality + ", validate=" + validate + ", donorSpliceSite=" + donorSpliceSite + ", acceptorSpliceSite=" + acceptorSpliceSite + ", mutation=" + mutation + ", europeanVarintFreq=" + europeanVarintFreq + ", africanVarintFreq=" + africanVarintFreq + ", asianVarintFreq=" + asianVarintFreq + ", americanVarintFreq=" + americanVarintFreq + ", wholeVarintFreq=" + wholeVarintFreq + '}';
    }

}
