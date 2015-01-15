import java.io.*
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

// Make a lot of output files

def parseFile = new File('indices/parse.tsv')

def headIndex = new File('indices/token2head.tsv')

OutputStreamWriter lemmaIndex = new OutputStreamWriter(new FileOutputStream('indices/token2lemma.tsv'), "UTF-8")

def inputFile = new File('further-syntax.tsv')

/** 
  def thisPos
  def thisPerson
  def thisNumber
  def thisTense
  def thisVoice
  def thisMood
  def thisCase
  def thisDegree
  def thisGender
  def thisLemma
  def thisToken
  def thisHeadToken
 **/

zeroFiles()

	inputFile.each{ l ->
		thisToken = l.tokenize("\t")[1]
			thisHeadToken = l.tokenize("\t")[3]
			thisLemma = l.tokenize("\t")[4]
			m = l =~ /\t[-a-z123]{9}/
		if (m.size() > 0){
			headIndex.append("${thisToken}\t${thisHeadToken}\n")
				lemmaIndex.append("${thisToken}\t${thisLemma}\n")	
				m[0].eachWithIndex{ c,i ->
					if (i > 0){
						posSwitch(c, i, thisToken)
					}
				}
		}
	}

lemmaIndex.close()

	println "done"

	def posSwitch( String c, int i, String thisToken){
		def parseFile = new File('indices/parse.tsv')
			def posFile = new File('indices/pos.tsv')
			def caseFile = new File('indices/case.tsv')
			def degreeFile = new File('indices/degree.tsv')
			def genderFile = new File('indices/gender.tsv')
			def moodFile = new File('indices/mood.tsv')
			def numberFile = new File('indices/number.tsv')
			def personFile = new File('indices/person.tsv')
			def tenseFile = new File('indices/tense.tsv')
			def voiceFile = new File('indices/voice.tsv')
			def whichFile 
			switch (i) {
				case 1:
					whichFile = posFile
						switch (c){
							case 'n':
								whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.noun\n")
									parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.noun\n")  
									break 
							case 'v':
									whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.verb\n")
										parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.verb\n")  
										break 
							case 't':
										whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.participle\n")
											parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.participle\n")  
											break 
							case 'a':
											whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.adjective\n")
												parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.adjective\n")  
												break
							case 'd':
												whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.adverb\n")
													parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.adverb\n")  
													break 
							case 'c':
													whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.conjunction\n")
														parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.conjunction\n")  
														break 
							case 'l':
														whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.article\n")
															parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.article\n")  
															break 
							case 'g':
															whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.particle\n")
																parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.particle\n")  
																break 
							case 'r':
																whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.preposition\n")
																	parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.preposition\n")  
																	break 
							case 'p':
																	whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.pronoun\n")
																		parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.pronoun\n")  
																		break 
							case 'm':
																		whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.numeral\n")
																			parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.numeral\n")  
																			break 
							case 'i':
																			whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.interjection\n")
																				parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.interjection\n")  
																				break 
							case 'e':
																				whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.exclamation\n")
																					parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.exclamation\n")  
																					break 
							case 'x':
																					whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.irregular\n")
																						parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.irregular\n")  
																						break 
							case 'u':
																						whichFile.append("${thisToken}\turn:cite:fuSyntax:pos.punctuation\n")
																							parseFile.append("${thisToken}\turn:cite:fuSyntax:pos.punctuation\n")  
																							break 
						}   
					break
				case 2:
						whichFile = personFile
							switch (c){
								case '1':
									whichFile.append("${thisToken}\turn:cite:fuSyntax:person.first_person\n")
										parseFile.append("${thisToken}\turn:cite:fuSyntax:person.first_person\n")  
										break
								case '2':
										whichFile.append("${thisToken}\turn:cite:fuSyntax:person.second_person\n")
											parseFile.append("${thisToken}\turn:cite:fuSyntax:person.second_person\n")  
											break
								case '3':
											whichFile.append("${thisToken}\turn:cite:fuSyntax:person.third_person\n")
												parseFile.append("${thisToken}\turn:cite:fuSyntax:person.third_person\n")  
												break
							}
						break
				case 3:
							whichFile = numberFile
								switch (c){
									case 's':
										whichFile.append("${thisToken}\turn:cite:fuSyntax:number.singular\n")
											parseFile.append("${thisToken}\turn:cite:fuSyntax:number.singular\n")  
											break
									case 'p':
											whichFile.append("${thisToken}\turn:cite:fuSyntax:number.plural\n")
												parseFile.append("${thisToken}\turn:cite:fuSyntax:number.plural\n")  
												break
									case 'd':
												whichFile.append("${thisToken}\turn:cite:fuSyntax:number.dual\n")
													parseFile.append("${thisToken}\turn:cite:fuSyntax:number.dual\n")  
													break
								}
							break
				case 4:
								whichFile = tenseFile
									switch (c){
										case 'p':
											whichFile.append("${thisToken}\turn:cite:fuSyntax:tense.present\n")
												parseFile.append("${thisToken}\turn:cite:fuSyntax:tense.present\n")  
												break
										case 'i':
												whichFile.append("${thisToken}\turn:cite:fuSyntax:tense.imperfect\n")
													parseFile.append("${thisToken}\turn:cite:fuSyntax:tense.imperfect\n")  
													break
										case 'r':
													whichFile.append("${thisToken}\turn:cite:fuSyntax:tense.perfect\n")
														parseFile.append("${thisToken}\turn:cite:fuSyntax:tense.perfect\n")  
														break
										case 'l':
														whichFile.append("${thisToken}\turn:cite:fuSyntax:tense.pluperfect\n")
															parseFile.append("${thisToken}\turn:cite:fuSyntax:tense.pluperfect\n")  
															break
										case 't':
															whichFile.append("${thisToken}\turn:cite:fuSyntax:tense.future_perfect\n")
																parseFile.append("${thisToken}\turn:cite:fuSyntax:tense.future_perfect\n")  
																break
										case 'f':
																whichFile.append("${thisToken}\turn:cite:fuSyntax:tense.future\n")
																	parseFile.append("${thisToken}\turn:cite:fuSyntax:tense.future\n")  
																	break
										case 'a':
																	whichFile.append("${thisToken}\turn:cite:fuSyntax:tense.aorist\n")
																		parseFile.append("${thisToken}\turn:cite:fuSyntax:tense.aorist\n")  
																		break
									}
								break
				case 5:
									whichFile = moodFile
										switch (c){
											case 'i':
												whichFile.append("${thisToken}\turn:cite:fuSyntax:mood.indicative\n")
													parseFile.append("${thisToken}\turn:cite:fuSyntax:mood.indicative\n")  
													break
											case 's':
													whichFile.append("${thisToken}\turn:cite:fuSyntax:mood.subjunctive\n")
														parseFile.append("${thisToken}\turn:cite:fuSyntax:mood.subjunctive\n")  
														break
											case 'o':
														whichFile.append("${thisToken}\turn:cite:fuSyntax:mood.optative\n")
															parseFile.append("${thisToken}\turn:cite:fuSyntax:mood.optative\n")  
															break
											case 'n':
															whichFile.append("${thisToken}\turn:cite:fuSyntax:mood.infinitive\n")
																parseFile.append("${thisToken}\turn:cite:fuSyntax:mood.infinitive\n")  
																break
											case 'm':
																whichFile.append("${thisToken}\turn:cite:fuSyntax:mood.imperative\n")
																	parseFile.append("${thisToken}\turn:cite:fuSyntax:mood.imperative\n")  
																	break
											case 'g':
																	whichFile.append("${thisToken}\turn:cite:fuSyntax:mood.gerundive\n")
																		parseFile.append("${thisToken}\turn:cite:fuSyntax:mood.gerundive\n")  
																		break
											case 'p':
																		whichFile.append("${thisToken}\turn:cite:fuSyntax:mood.participial\n")
																			parseFile.append("${thisToken}\turn:cite:fuSyntax:mood.participial\n")  
																			break
										}
									break
				case 6:
										whichFile = voiceFile
											switch (c){
												case 'a':
													whichFile.append("${thisToken}\turn:cite:fuSyntax:voice.active\n")
														parseFile.append("${thisToken}\turn:cite:fuSyntax:voice.active\n")  
														break
												case 'p':
														whichFile.append("${thisToken}\turn:cite:fuSyntax:voice.passive\n")
															parseFile.append("${thisToken}\turn:cite:fuSyntax:voice.passive\n")  
															break
												case 'd':
															whichFile.append("${thisToken}\turn:cite:fuSyntax:voice.deponent\n")
																parseFile.append("${thisToken}\turn:cite:fuSyntax:voice.deponent\n")  
																break
												case 'e':
																whichFile.append("${thisToken}\turn:cite:fuSyntax:voice.medio_passive\n")
																	parseFile.append("${thisToken}\turn:cite:fuSyntax:voice.medio_passive\n")  
																	break
											}
										break
				case 7:
											whichFile = genderFile
												switch (c){
													case 'm':
														whichFile.append("${thisToken}\turn:cite:fuSyntax:gender.masculine\n")
															parseFile.append("${thisToken}\turn:cite:fuSyntax:gender.masculine\n")  
															break
													case 'f':
															whichFile.append("${thisToken}\turn:cite:fuSyntax:gender.feminine\n")
																parseFile.append("${thisToken}\turn:cite:fuSyntax:gender.feminine\n")  
																break
													case 'n':
																whichFile.append("${thisToken}\turn:cite:fuSyntax:gender.neuter\n")
																	parseFile.append("${thisToken}\turn:cite:fuSyntax:gender.neuter\n")  
																	break
												}
											break
				case 8:
												whichFile = caseFile
													switch (c){
														case 'n':
															whichFile.append("${thisToken}\turn:cite:fuSyntax:case.nominative\n")
																parseFile.append("${thisToken}\turn:cite:fuSyntax:case.nominative\n")  
																break
														case 'g':
																whichFile.append("${thisToken}\turn:cite:fuSyntax:case.genitive\n")
																	parseFile.append("${thisToken}\turn:cite:fuSyntax:case.genitive\n")  
																	break
														case 'd':
																	whichFile.append("${thisToken}\turn:cite:fuSyntax:case.dative\n")
																		parseFile.append("${thisToken}\turn:cite:fuSyntax:case.dative\n")  
																		break
														case 'a':
																		whichFile.append("${thisToken}\turn:cite:fuSyntax:case.accusative\n")
																			parseFile.append("${thisToken}\turn:cite:fuSyntax:case.accusative\n")  
																			break
														case 'b':
																			whichFile.append("${thisToken}\turn:cite:fuSyntax:case.ablative\n")
																				parseFile.append("${thisToken}\turn:cite:fuSyntax:case.ablative\n")  
																				break
														case 'i':
																				whichFile.append("${thisToken}\turn:cite:fuSyntax:case.instrumental\n")
																					parseFile.append("${thisToken}\turn:cite:fuSyntax:case.instrumental\n")  
																					break
														case 'l':
																					whichFile.append("${thisToken}\turn:cite:fuSyntax:case.locative\n")
																						parseFile.append("${thisToken}\turn:cite:fuSyntax:case.locative\n")  
																						break
													}
												break
				case 9:
													whichFile = degreeFile
														switch (c){
															case 'p':
																whichFile.append("${thisToken}\turn:cite:fuSyntax:voice.positive\n")
																	parseFile.append("${thisToken}\turn:cite:fuSyntax:voice.positive\n")  
																	break
															case 'c':
																	whichFile.append("${thisToken}\turn:cite:fuSyntax:voice.comparative\n")
																		parseFile.append("${thisToken}\turn:cite:fuSyntax:voice.comparative\n")  
																		break
															case 's':
																		whichFile.append("${thisToken}\turn:cite:fuSyntax:voice.superlative\n")
																			parseFile.append("${thisToken}\turn:cite:fuSyntax:voice.superlative\n")  
																			break
														}
													break
			}


	}

def zeroFiles(){

	def posFile = new File('indices/pos.tsv')
		posFile.write('')
		def caseFile = new File('indices/case.tsv')
		caseFile.write('')
		def degreeFile = new File('indices/degree.tsv')
		degreeFile.write('')
		def genderFile = new File('indices/gender.tsv')
		genderFile.write('')
		def moodFile = new File('indices/mood.tsv')
		moodFile.write('')
		def numberFile = new File('indices/number.tsv')
		numberFile.write('')
		def personFile = new File('indices/person.tsv')
		personFile.write('')
		def tenseFile = new File('indices/tense.tsv')
		tenseFile.write('')
		def voiceFile = new File('indices/voice.tsv')
		voiceFile.write('')

}
