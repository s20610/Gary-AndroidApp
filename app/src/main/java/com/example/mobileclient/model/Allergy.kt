package com.example.mobileclient.model

/**
 * This class represents info about allergy
 *
 * <b> Sample usage <b>
 *
 * ```
 * Allergy("Orzechy","Jedzenie")
 * ```
 *
 * @param allergyName String name of allergy
 * @param allergyType String type of allergy
 *
 * @see com.example.mobileclient.adapter.AllergyAdapter
 */
data class Allergy (val allergyName: String,val allergyType : String)
//TODO(Allergy): Should allergy contain extra info or no