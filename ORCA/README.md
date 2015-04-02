# Notes on ORCAs

## Steps

1. Edit the XML text in TEI.
1. Run CITE Manager to generate a tabulated expression of the XML.
1. Put tab-files in the `Tabs` directory.
1. `groovy tokenizeDrama.groovy` will read all `*.txt` files in `Tabs/` and generate a file named `syntaxTokens.tsv`  

## Building the Analytical Examplar

(Currently this script works only with Groovy v.2.2.1, for some reason.)

> `groovy buildExemplar.groovy syntaxTokens.tsv citeUrn fuTok ctsUrn analysis surfaceForm`

## Alignment

The above steps will generate an Analytical Exemplar with each word-token and significant punctuation-mark citeable.

It will be necessary to align this with previously generated syntax files. 

The result of this will be *two* Analytical Examplars: one citeable by "syntax token", and the other by "treebank token".

For syntax, the most common re-alignment is when the text has "οὔτε", which is analyzed as two tokens, the adverb "οὔ" and the conjunction "τε", for treebanking. So let's call "οὔτε" a "syntax-token" and the two, "οὔ" and "τε", we will call "treebank tokens."

The `syntaxTokens.tsv` file will contain a like like this:

> 133	urn:cite:fu:synTok_tlg0085_tlg003_fu.133	urn:cts:greekLit:tlg0085.tlg003.fu:21@οὔτε[1]	urn:cite:fu:synTok_tlg0085_tlg003_fu.133	οὔτε

That is:

| Property | Value |
|----------|-------|
| Sequence | 133 |
| CITE-URN of token | urn:cite:fu:synTok_tlg0085_tlg003_fu.133 |
| Analyzed Content | urn:cts:greekLit:tlg0085.tlg003.fu:21@οὔτε[1] |
| CITE-URN of Analysis | urn:cite:fu:synTok_tlg0085_tlg003_fu.133 |
| Presentational Form | οὔτε |

(In this case, the CITE-URN of the token  and the "analysis" are the same.)

We need to align two treebank-analyses with a single syntax-token.



urn:cite:fu:synTok_tlg0085_tlg003_fu.133	οὔ
urn:cite:fu:synTok_tlg0085_tlg003_fu.133	τε
