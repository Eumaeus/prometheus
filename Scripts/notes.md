# Aligned Versions in CTS

Take for example these two texts, represented by the XML fragments below.

~~~~~~~~~~~~
<div>
        <l n="1"> ὦ τέκνα, Κάδμου τοῦ πάλαι νέα τροφή, </l>
        <l n="2"> τίνας ποθʼ ἕδρας τάσδε μοι θοάζετε </l>
        <l n="3"> ἱκτηρίοις κλάδοισιν ἐξεστεμμένοι; </l>
        <l n="4"> πόλις δʼ ὁμοῦ μὲν θυμιαμάτων γέμει, </l>
        <l n="5"> ὁμοῦ δὲ παιάνων τε καὶ στεναγμάτων: </l>
        <l n="6"> ἁγὼ δικαιῶν μὴ παρʼ ἀγγέλων, τέκνα, </l>
        <l n="7"> ἄλλων ἀκούειν αὐτὸς ὧδʼ ἐλήλυθα, </l>
        <l n="8"> ὁ πᾶσι κλεινὸς Οἰδίπους καλούμενος. </l>
        <l n="9"> ἀλλʼ ὦ γεραιέ, φράζʼ, ἐπεὶ πρέπων ἔφυς </l>
        <l n="10"> πρὸ τῶνδε φωνεῖν, τίνι τρόπῳ καθέστατε, </l>
        <l n="11"> δείσαντες ἢ στέρξαντες; ὡς θέλοντος ἂν </l>
        <l n="12"> ἐμοῦ προσαρκεῖν πᾶν: δυσάλγητος γὰρ ἂν </l>
        <l n="13"> εἴην τοιάνδε μὴ οὐ κατοικτίρων ἕδραν. </l>
</div>
~~~~~~~~~~~~
~~~~~~~~~~~~
<div>
        <p>
                <seg n="1">My children, latest-born wards of old Cadmus, 
                why do you sit before me like
                this with wreathed branches of suppliants, 
                while the city reeks with incense,</seg>
                <seg n="5">rings with prayers for health and cries of woe? 
                I thought it unbefitting, my
                children, to hear these things from the mouths of others, 
                and have come here myself,
                I, Oedipus renowned by all. Tell me, then, venerable old man—
                since it is proper that you</seg>
                <seg n="10">speak for these—
                in what mood you sit here, one of fear or of desire? 
                Be sure that I will gladly give you all my help. 
                I would be hard-hearted indeed if I did not
                pity such suppliants as these.</seg>
        </p>
</div>
~~~~~~~~~~~~

The first is an Edition of a Greek text, cited by poetic line. The second is a translation; the language does not line up neatly with the poetic lines of the original Greek, but the embedded citation scheme identifies sections in terms of that canonical citation. 'Segment' 1 corresponds to lines 1–4; 'Segment 5' to 5–9; and 'Segment 10' to lines 10–14.

The first passage might be from a text that we cite with this URN:

> `<urn:cts:greeklit:tlg0011.tlg004.grc>`

The translation is a text, with its own "ordered hierarcy of citation-objects". We could cite it with its own URN:

> `<urn:cts:greekLit:tlg0011.tlg004.eng>`

And we can address the citeable elements of both texts:


> `<urn:cts:greekLit:tlg0011.tlg004.grc:1>`
> `<urn:cts:greekLit:tlg0011.tlg004.eng:1>`

But while we can cite every poetic line of the Greek text, we cannot do the same with the translation.

So we need to **realign** the translation according to the **OHCO2** model, through a process of **analysis**, so we can **cite** it according to the **Canonical Citation Scheme** for this play.

## An Aligned Version is an ORCA

In thinking about "OHCO2 Realigned Citeable Analyses", we have focused on 'tokenized editions', based on a canonically cited edition, with an extra level of citation added, enabling work at a finer level of granularity.

An Aligned Translation is more-or-less the opposite: A derivative edition that operates only on a _more coarse_ level of granularity.

The _content elements_ of a 'token edition' may be very different from the content of the edition on which it is based—e.g. a 'metrical-syllable' edition, or a 'lemma edition'. But the base- and token-edition share a citation scheme, with the latter being finer-grained than the former.

An Aligned Version (AV henceforth) has _the same textual content_ as the Version on which it is based, but a coarser-grained citation scheme.


# The Base Version

In this case, it is a translation, but it could be an edition. We can treat it like any other. Here is 

    `urn:cts:greeklit:tlg0011.tlg004.eng`
    `urn:cts:greeklit:tlg0011.tlg004.eng:1`
    `urn:cts:greeklit:tlg0011.tlg004.eng:5-10`

# The Aligned Version

After _alignment_, we will have a _citable analysis_, consisting of URNs that capture the Valid References for the _base edition_. 

# An Aligned Translation is an ORCA

On the back-end, implementation:

<urn:cts:latinLit:phi0690.phi003.fuEngAeneidAligned> rdf:type cts:Alignment .

<urn:cts:latinLit:phi0690.phi003.fuPers> cts:aligns <urn:cts:latinLit:phi0690.phi003.fuEngAeneidAligned> .
<urn:cts:latinLit:phi0690.phi003.fuEngAeneidAligned> cts:alignsTo <urn:cts:latinLit:phi0690.phi003.fuPers> .

<urn:cts:latinLit:phi0690.phi003.fuEngAligned:1.3> cts:isComprehendedBy <urn:cts:latinLit:phi0690.phi003.fuEng:1.1> .
<urn:cts:latinLit:phi0690.phi003.fuEng:1.1> cts:comprehends <urn:cts:latinLit:phi0690.phi003.fuEngAligned:1.3> .

# GetPassage, e.g.

- Is version an rdf:type=cts:Alignment?
- If so, what alignmentRange comprehends this citation?
- And for what URN is that alignmentRange for?
- Get that, process it like any CTS text.

## GetPassage (range) e.g.

- 

## Advantages

- Texts get tabulated/RDFed as always.
- Alignment is merely an additional task in the build.
- This doesn't touch the CTS protocol of the CTS-URN protocol.
- GVR, etc., give you truth and what you ask for:
    - Ask for the (unaligned) translation, you get citation-values that are present in it.
    - Ask for the aligned translation, you get citation-values that are the result of the alignment.


urn:cts:latinLit:some.aligned.translation
<div n="1">
    <lg n="1-10">
    <lg n="11-20">
    <lg n="21-30">
</div>




