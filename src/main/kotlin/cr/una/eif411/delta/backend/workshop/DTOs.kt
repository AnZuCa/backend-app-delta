package cr.una.eif411.delta.backend.workshop

import java.util.*

data class StatusDetails(
    var id: Long? = null,
    var name: String? = null,
)


data class PrivilegeDetails(
    var id: Long? = null,
    var name: String? = null,
)

data class RoleDetails(
    var id: Long? = null,
    var name: String? = null,
    var privileges: List<PrivilegeDetails>? = null,
)

data class UserInput(
    var id: Long? = null,
    var idCard: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var createDate: Date? = null,
    var address: String? = null,
    var mobileNumber: String? = null,
    var password: String? = null,
    var enabled: Boolean? = null,
    var roleList: Set<RoleDetails>? = null,
)

data class UserLoginInput(
    var username: String = "",
    var password: String = "",
)

data class UserResult(
    var id: Long,
    var idCard: String?=null,
    var firstName: String,
    var lastName: String,
    var email: String,
    var enabled: Boolean?,
    var tokenExpired: Boolean?,
    var address: String,
    var mobileNumber: String,
    var createDate: Date,
    var roleList: List<RoleDetails>,
)

data class UserRepairResult(
    var id: Long,
    var idCard: String?=null,
    var firstName: String,
    var lastName: String,
    var email: String,
    var enabled: Boolean,
    var tokenExpired: Boolean,
    var createDate: Date,
    var roles: List<RoleDetails>,
    var clientRepairList: List<RepairResult>?=null,
)

data class VehicleDetails(
    var id: Long? = null,
    var plateNumber: String?=null,
    var vinNumber: String?=null,
    var engineNumber: String?=null,
    var vehicleBrand: String?=null,
    var vehicleType: String?=null,
    var engineType: String?=null
)

data class ServiceDetails(
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null
)


data class RepairInput(
    var id: Long? = null,
    var description: String? = null,
    var mechanic: UserInput? = null,
    var client: UserInput? = null,
    var vehicle: VehicleDetails? = null,
    var registrationDate: Date? = null,
    var status: StatusDetails? = null,
    var serviceList: Set<ServiceDetails>? = null,
    var vehicleImageList: List<VehicleImageDetails>? = null
)

data class RepairResult(
    var id: Long,
    var description: String,
    var mechanic: UserResult,
    var client: UserResult,
    var vehicle: VehicleDetails,
    var status: StatusDetails,
    var serviceList: Set<ServiceDetails>,
    var vehicleImageList: List<VehicleImageDetails>? = null
)

data class VehicleImageDetails(
    var id: Long? = null,
    var imagePath: String? = null
)