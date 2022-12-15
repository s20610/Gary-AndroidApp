package com.example.mobileclient.api

import com.example.mobileclient.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

object Repository {
    suspend fun getLoginResponse(credentials: Credentials): Response<AuthResponse> {
        return RetrofitInstance.api.getLoginResponse(credentials)
    }

    suspend fun registerNewUser(newUser: NewUser): Response<ResponseBody> {
        return RetrofitInstance.api.registerNewUser(newUser)
    }

    suspend fun getTutorials(): Response<List<Tutorial>> {
        return RetrofitInstance.api.getTutorials()
    }

    suspend fun getTutorial(id: Int): Response<Tutorial> {
        return RetrofitInstance.api.getTutorial(id)
    }

    suspend fun addTutorialRating(tutorialId: Int, email: String): Response<ResponseBody> {
        return RetrofitInstance.api.addTutorialRating(tutorialId, email)
    }

    suspend fun getUserMedicalInfo(userEmail: String): Response<MedicalInfo> {
        return RetrofitInstance.api.getUserMedicalInfo(userEmail)
    }

    suspend fun getUserBlood(id: Int): Response<Blood> {
        return RetrofitInstance.api.getUserBlood(id)
    }

    suspend fun putUserBlood(id: Int, blood: Blood): Response<ResponseBody> {
        return RetrofitInstance.api.putUserBlood(id, blood)
    }

    suspend fun postUserBlood(blood: Blood): Response<ResponseBody> {
        return RetrofitInstance.api.postUserBlood(blood)
    }

    suspend fun getUserDisease(id: Int): Response<Disease> {
        return RetrofitInstance.api.getDisease(id)
    }

    suspend fun putUserDisease(id: Int, disease: Disease): Response<ResponseBody> {
        return RetrofitInstance.api.putUserDisease(id, disease)
    }

    suspend fun deleteUserDisease(id: Int): Response<ResponseBody> {
        return RetrofitInstance.api.deleteUserDisease(id)
    }

    suspend fun postUserDisease(disease: Disease): Response<ResponseBody> {
        return RetrofitInstance.api.postUserDisease(disease)
    }

    suspend fun getUserAllergy(id: Int): Response<Allergy> {
        return RetrofitInstance.api.getAllergy(id)
    }

    suspend fun putUserAllergy(id: Int, allergy: Allergy): Response<ResponseBody> {
        return RetrofitInstance.api.putUserAllergy(id, allergy)
    }

    suspend fun postUserAllergy(
        allergy: Allergy
    ): Response<ResponseBody> {
        return RetrofitInstance.api.postUserMedicalInfoAllergies(allergy)
    }

    suspend fun deleteUserAllergy(id: Int): Response<ResponseBody> {
        return RetrofitInstance.api.deleteUserAllergy(id)
    }

    //Enum types

    suspend fun getAllergyTypes(): Response<List<String>> {
        return RetrofitInstance.api.getAllergyTypes()
    }

    suspend fun getRhTypes(): Response<List<String>> {
        return RetrofitInstance.api.getRhTypes()
    }

    suspend fun getBloodTypes(): Response<List<String>> {
        return RetrofitInstance.api.getBloodTypes()
    }

    suspend fun getAmbulanceTypes(): Response<List<String>> {
        return RetrofitInstance.api.getAmbulanceTypes()
    }

    suspend fun getAmbulanceStates(): Response<List<String>> {
        return RetrofitInstance.api.getAmbulanceStates()
    }

    suspend fun getAmbulanceClasses(): Response<List<String>> {
        return RetrofitInstance.api.getAmbulanceClasses()
    }

    suspend fun getEmergencyTypes(): Response<List<String>> {
        return RetrofitInstance.api.getEmergencyTypes()
    }

    suspend fun getFacilityTypes(): Response<List<String>> {
        return RetrofitInstance.api.getFacilityTypes()
    }

    // Trusted Person
    suspend fun getTrustedPerson(email: String): Response<TrustedPerson> {
        return RetrofitInstance.api.getTrustedPerson(email)
    }

    suspend fun putTrustedPerson(trustedPerson: TrustedPerson): Response<ResponseBody> {
        return RetrofitInstance.api.putTrustedPerson(trustedPerson)
    }

    suspend fun postTrustedPerson(trustedPerson: TrustedPerson): Response<ResponseBody> {
        return RetrofitInstance.api.postTrustedPerson(trustedPerson)
    }

    suspend fun deleteTrustedPerson(email: String): Response<ResponseBody> {
        return RetrofitInstance.api.deleteTrustedPerson(email)
    }

    //Accident Report

    suspend fun getAccidentReport(id: Int): Response<AccidentReport> {
        return RetrofitInstance.api.getAccidentReport(id)
    }

    suspend fun getAccidentReports(userEmail: String): Response<List<AccidentReport>> {
        return RetrofitInstance.api.getUserAccidentReports(userEmail)
    }

    suspend fun postAccidentReport(accidentReport: AccidentReport): Response<ResponseBody> {
        return RetrofitInstance.api.postAccidentReport(accidentReport)
    }

    //Employee shift
    suspend fun startEmployeeShift(token: String): Response<ResponseBody> {
        return RetrofitInstance.api.startShift(token)
    }

    suspend fun endEmployeeShift(token: String): Response<ResponseBody> {
        return RetrofitInstance.api.endShift(token)
    }

    suspend fun getEmployeeShifts(token: String): Response<WholeSchedule> {
        return RetrofitInstance.api.getSchedule(token)
    }

    //Facilities
    suspend fun getFacilities(): Response<List<Facility>> {
        return RetrofitInstance.api.getFacilities()
    }

    //Ambulance
    suspend fun getAmbulanceEquipment(licensePlate: String): Response<List<AmbulanceEquipment>> {
        return RetrofitInstance.api.getAmbulanceEquipment(licensePlate)
    }

    suspend fun changeAmbulanceState(licensePlate: String, state: String): Response<ResponseBody> {
        return RetrofitInstance.api.changeAmbulanceState(licensePlate, state)
    }

    suspend fun updateAmbulanceLocation(
        licensePlate: String,
        location: Location
    ): Response<ResponseBody> {
        return RetrofitInstance.api.updateAmbulanceLocation(licensePlate, location)
    }

    suspend fun addAmbulanceItem(licensePlate: String, itemId: Int): Response<ResponseBody> {
        return RetrofitInstance.api.addAmbulanceItem(licensePlate, itemId)
    }

    suspend fun removeAmbulanceItem(licensePlate: String, itemId: Int): Response<ResponseBody> {
        return RetrofitInstance.api.removeAmbulanceItem(licensePlate, itemId)
    }

    suspend fun getAssignedAmbulance(token: String): Response<Ambulance> {
        return RetrofitInstance.api.getAssignedAmbulance(token)
    }

}