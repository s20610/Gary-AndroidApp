package com.example.mobileclient.model

data class Backup(
    var id: Int?, var requester: String?,
    var incidentId: Int?,
    var accepted: Boolean?,
    var justification: String?,
    var backupType: BackupType?
) {
   companion object {
       enum class BackupType(val type: String) {
           //"POLICE",
           //  "FIRE_FIGHTERS",
           //  "AMBULANCE",
           //  "POLICE_AMBULANCE",
           //  "POLICE_FIRE",
           //  "AMBULANCE_FIRE",
           //  "POLICE_AMBULANCE_FIRE"
              POLICE("POLICE"),
                FIRE_FIGHTERS("FIRE_FIGHTERS"),
                AMBULANCE("AMBULANCE"),
                POLICE_AMBULANCE("POLICE_AMBULANCE"),
                POLICE_FIRE("POLICE_FIRE"),
                AMBULANCE_FIRE("AMBULANCE_FIRE"),
                POLICE_AMBULANCE_FIRE("POLICE_AMBULANCE_FIRE")
       }
   }
}