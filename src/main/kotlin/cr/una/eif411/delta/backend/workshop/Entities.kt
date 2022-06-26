package cr.una.eif411.delta.backend.workshop

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "vehicle")
data class Vehicle(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "vehicle_sequence")
    @SequenceGenerator(name = "vehicle_sequence", sequenceName = "vehicle_sequence", allocationSize = 1)
    var id: Long? = null,
    var plateNumber: String? = null,
    var vinNumber: String? = null,
    var engineNumber: String? = null,
    var vehicleBrand: String? = null,
    var vehicleType: String? = null,
    var engineType: String? = null,
    @OneToMany(mappedBy = "vehicle")
    var repairList: List<Repair>? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vehicle

        if (id != other.id) return false
        if (plateNumber != other.plateNumber) return false
        if (vinNumber != other.vinNumber) return false
        if (engineNumber != other.engineNumber) return false
        if (vehicleBrand != other.vehicleBrand) return false
        if (vehicleType != other.vehicleType) return false
        if (engineType != other.engineType) return false
        if (repairList != other.repairList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (plateNumber?.hashCode() ?: 0)
        result = 31 * result + (vinNumber?.hashCode() ?: 0)
        result = 31 * result + (engineNumber?.hashCode() ?: 0)
        result = 31 * result + (vehicleBrand?.hashCode() ?: 0)
        result = 31 * result + (vehicleType?.hashCode() ?: 0)
        result = 31 * result + (engineType?.hashCode() ?: 0)
        result = 31 * result + (repairList?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Vehicle(id=$id, plateNumber=$plateNumber, vinNumber=$vinNumber, engineNumber=$engineNumber, vehicleBrand=$vehicleBrand, vehicleType=$vehicleType, engineType=$engineType, repairList=$repairList)"
    }
}

@Entity
@Table(name = "service")
data class Service(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "service_sequence")
    @SequenceGenerator(name = "service_sequence", sequenceName = "service_sequence", allocationSize = 1)
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    @ManyToMany(mappedBy = "serviceList", fetch = FetchType.LAZY)
    var repairList: List<Repair>? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Service

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (repairList != other.repairList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (repairList?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Service(id=$id, name=$name, description=$description, repairList=$repairList)"
    }
}

@Entity
@Table(name = "status")
data class Status(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "status_sequence")
    @SequenceGenerator(name = "status_sequence", sequenceName = "status_sequence", allocationSize = 1)
    var id: Long? = null,
    var name: String? = null,
    @OneToMany(mappedBy = "status")
    var repairList: List<Repair>? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Status

        if (id != other.id) return false
        if (name != other.name) return false
        if (repairList != other.repairList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (repairList?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Status(id=$id, name=$name, repairList=$repairList)"
    }
}

@Entity
@Table(name = "repair")
data class Repair(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "repair_sequence")
    @SequenceGenerator(name = "repair_sequence", sequenceName = "repair_sequence", allocationSize = 1)
    var id: Long? = null,
    var description: String,
    @Temporal(TemporalType.DATE)
    var registrationDate: Date,
    @Temporal(TemporalType.DATE)
    var departureDate: Date? = null,
    @ManyToOne
    @JoinColumn(name = "mechanic_id", nullable = false, referencedColumnName = "id")
    var mechanic: User,
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "id")
    var client: User,
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false, referencedColumnName = "id")
    var vehicle: Vehicle,
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
    var status: Status,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "repair_service",
        joinColumns = [JoinColumn(name = "repair_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "service_id", referencedColumnName = "id")]
    )
    var serviceList: Set<Service>,
    @OneToMany(mappedBy = "repair")
    var vehicleImageList: List<VehicleImage>? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Repair

        if (id != other.id) return false
        if (description != other.description) return false
        if (registrationDate != other.registrationDate) return false
        if (departureDate != other.departureDate) return false
        if (mechanic != other.mechanic) return false
        if (client != other.client) return false
        if (vehicle != other.vehicle) return false
        if (status != other.status) return false
        if (serviceList != other.serviceList) return false
        if (vehicleImageList != other.vehicleImageList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + description.hashCode()
        result = 31 * result + registrationDate.hashCode()
        result = 31 * result + (departureDate?.hashCode() ?: 0)
        result = 31 * result + mechanic.hashCode()
        result = 31 * result + client.hashCode()
        result = 31 * result + vehicle.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + serviceList.hashCode()
        result = 31 * result + (vehicleImageList?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Repair(id=$id, description='$description', registrationDate=$registrationDate, departureDate=$departureDate, mechanic=$mechanic, client=$client, vehicle=$vehicle, status=$status, serviceList=$serviceList, vehicleImageList=$vehicleImageList)"
    }
}

@Entity
@Table(name = "vehicle_image")
data class VehicleImage(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "vImage_sequence")
    @SequenceGenerator(name = "vImage_sequence", sequenceName = "vImage_sequence", allocationSize = 1)
    var id: Long? = null,
    var imagePath: String,
    @ManyToOne
    @JoinColumn(name = "repair_id", nullable = false, referencedColumnName = "id")
    var repair: Repair,



) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VehicleImage

        if (id != other.id) return false
        if (imagePath != other.imagePath) return false
        if (repair != other.repair) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + imagePath.hashCode()
        result = 31 * result + repair.hashCode()
        return result
    }

    override fun toString(): String {
        return "VehicleImage(id=$id, imagePath='$imagePath', repair=$repair)"
    }


}

@Entity
@Table(name = "role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "role_sequence")
    @SequenceGenerator(name = "role_sequence", sequenceName = "role_sequence", allocationSize = 1)
    var id: Long? = null,
    var name: String? = null,
    @ManyToMany
    @JoinTable(
        name = "role_privilege",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")]
    )
    var privilegeList: Set<Privilege>? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Role) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Role(id=$id, name='$name', privilegeList=$privilegeList)"
    }
}

@Entity
@Table(name = "privilege")
data class Privilege(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "privilege_sequence")
    @SequenceGenerator(name = "privilege_sequence", sequenceName = "privilege_sequence", allocationSize = 1)
    var id: Long? = null,
    var name: String,
    @ManyToMany(mappedBy = "roleList", fetch = FetchType.LAZY)
    var userList: Set<User>,
    @ManyToMany(mappedBy = "privilegeList", fetch = FetchType.LAZY)
    var roleList: Set<Role>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Privilege) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Privilege(id=$id, name='$name', userList=$userList, roleList=$roleList)"
    }
}

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "users_sequence")
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence", allocationSize = 1)
    var id: Long? = null,
    var idCard: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var password: String? = null,
    var email: String? = null,
    var address: String? = null,
    var mobileNumber: String? = null,
    var createDate: Date? = null,
    var enabled: Boolean,
    var tokenExpired: Boolean? = null,
    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roleList: Set<Role>? = null,
    @OneToMany(mappedBy = "client")
    var clientRepairList: List<Repair>? = null,
    @OneToMany(mappedBy = "mechanic")
    var mechanicRepairList: List<Repair>? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + email.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(id=$id, idCard=$idCard, firstName=$firstName, lastName=$lastName, password=$password, " +
                "email=$email, address=$address, mobileNumber=$mobileNumber, createDate=$createDate, " +
                "enabled=$enabled, tokenExpired=$tokenExpired, roleList=$roleList, " +
                "clientRepairList=$clientRepairList, mechanicRepairList=$mechanicRepairList)"
    }


}
