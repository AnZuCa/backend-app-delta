package cr.una.eif411.delta.backend.workshop

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityManager
import javax.transaction.Transactional
import kotlin.NoSuchElementException

interface RepairService {
    fun findAll(): List<RepairResult>?
    fun findById(id: Long): RepairResult?
    fun findByMechanicId(id: Long): List<RepairResult>?
    fun findByClientId(id: Long): List<RepairResult>?
    fun create(repairInput: RepairInput): RepairResult?
    fun update(repairInput: RepairInput): RepairResult?
}

interface VehicleService {
    fun findAll(): List<VehicleDetails>?
    fun findById(id: Long): VehicleDetails?
    fun findByPlateNumber(plateNumber:String): VehicleDetails?
    fun findByVinNumber(vinNumber:String): VehicleDetails?
    fun create(vehicleInput: VehicleDetails): VehicleDetails?
    fun update(vehicleInput: VehicleDetails): VehicleDetails?
}

interface UserService {
    fun findAll(roleId: Long?): List<UserResult>?
    fun findById(id: Long): UserResult?
    fun findByEmail(email: String): UserResult?
    fun findByIdCard(idCard: String): UserResult?
    fun findByIdAndRole(id: Long, role: Role): UserResult?
    fun create(userInput: UserInput): UserResult?
    fun update(userInput: UserInput): UserResult?
}

interface ServiceService {
    fun findAll(): List<ServiceDetails>?
    fun findById(id: Long): ServiceDetails?

}



@Service
class AbstractRepairService(
    @Autowired
    val repairRepository: RepairRepository,
    @Autowired
    val roleRepository: RoleRepository,
    @Autowired
    val userService: AbstractUserService,
    @Autowired
    val vehicleService: AbstractVehicleService,
    @Autowired
    val repairMapper: RepairMapper,
    @Autowired
    val entityManager: EntityManager,
) : RepairService {

    override fun findAll(): List<RepairResult>? {
        return repairMapper.repairListToRepairListResult(repairRepository.findAll())
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): RepairResult? {
        val repair = repairRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException("The Repair with the id: $id not found!")

        return repairMapper.repairToRepairResult(repair)
    }

    @Throws(NoSuchElementException::class)
    override fun findByMechanicId(id: Long): List<RepairResult>? {
        val mechanicRole = roleRepository.findByName("mechanic").orElse(null)

        try {
            userService.findByIdAndRole(id, mechanicRole)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException("The Mechanic with the id: $id not found!")
        }

        val repairs = repairRepository.findByMechanicId(id).orElse(null)
        return repairMapper.repairListToRepairListResult(repairs)
    }

    @Throws(NoSuchElementException::class)
    override fun findByClientId(id: Long): List<RepairResult>? {
        val clientRole = roleRepository.findByName("client").orElse(null)

        try {
            userService.findByIdAndRole(id, clientRole)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException("The Client with the id: $id not found!")
        }

        val repairs = repairRepository.findByClientId(id)
        return repairMapper.repairListToRepairListResult(repairs.get())
    }

    @Transactional
    override fun create(repairInput: RepairInput): RepairResult? {
        // If the user doesn't exist, create it
        if (repairInput.client != null && repairInput.client!!.id == null) {
            val savedClient = userService.create(repairInput.client!!)
            repairInput.client!!.id = savedClient!!.id
        }

        // If the vehicle doesn't exist, create it
        if (repairInput.vehicle != null && repairInput.vehicle!!.id == null) {
            val savedVehicle = vehicleService.create(repairInput.vehicle!!)
            repairInput.vehicle!!.id = savedVehicle!!.id
        }

        val repair = repairMapper.repairInputToRepair(repairInput)
        val savedRepair = repairRepository.saveAndFlush(repair)
        entityManager.refresh(savedRepair)

        return repairMapper.repairToRepairResult(savedRepair)
    }

    @Throws(NoSuchElementException::class)
    override fun update(repairInput: RepairInput): RepairResult? {
        val repair = repairRepository.findById(repairInput.id!!).orElse(null)
            ?: throw NoSuchElementException("The Repair with the id: ${repairInput.id} not found!")

        // Workaround to update the status. Try to find a better solution.
        if (repairInput.status != null) {
            repair.status = Status(repairInput.status?.id)
        }

        repairMapper.repairInputToRepair(repairInput, repair)

        return repairMapper.repairToRepairResult(repairRepository.save(repair))
    }
}

@Service
class AbstractVehicleService(
    @Autowired
    val vehicleRepository: VehicleRepository,
    @Autowired
    val vehicleMapper: VehicleMapper,
) : VehicleService {

    override fun findAll(): List<VehicleDetails>? {
        return vehicleMapper.vehicleListToVehicleListDetails(vehicleRepository.findAll())
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): VehicleDetails? {
        val vehicle = vehicleRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException(String.format("The vehicle with the id: %s not found!", id))

        return vehicleMapper.vehicleToVehicleDetails(vehicle)
    }

    @Throws(NoSuchElementException::class)
    override fun findByPlateNumber(plateNumber: String): VehicleDetails? {
        val vehicle = vehicleRepository.findByPlateNumber(plateNumber).orElse(null)
            ?: throw NoSuchElementException(String.format("The vehicle with the plate number: %s not found!", plateNumber))

        return vehicleMapper.vehicleToVehicleDetails(vehicle)
    }

    @Throws(NoSuchElementException::class)
    override fun findByVinNumber(vinNumber: String): VehicleDetails? {
        val vehicle = vehicleRepository.findByVinNumber(vinNumber).orElse(null)
            ?: throw NoSuchElementException(String.format("The vehicle with the vin number: %s not found!", vinNumber))

        return vehicleMapper.vehicleToVehicleDetails(vehicle)
    }

    override fun create(vehicleInput: VehicleDetails): VehicleDetails? {
        val vehicle = vehicleMapper.vehicleDetailsToVehicle(vehicleInput)
        return vehicleMapper.vehicleToVehicleDetails(vehicleRepository.save(vehicle))
    }

    @Throws(NoSuchElementException::class)
    override fun update(vehicleInput: VehicleDetails): VehicleDetails? {
        val vehicle = vehicleRepository.findById(vehicleInput.id!!).orElse(null)
            ?: throw NoSuchElementException(String.format("The Vehicle with the id: %s not found!", vehicleInput.id))

        val vehicleUpdated = vehicle
        vehicleMapper.vehicleDetailsToVehicle(vehicleInput, vehicleUpdated)

        return vehicleMapper.vehicleToVehicleDetails(vehicleRepository.save(vehicleUpdated))
    }
}

@Service
class AbstractUserService(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val userMapper: UserMapper,
    @Autowired
    val entityManager: EntityManager,
) : UserService {

    override fun findAll(roleId: Long?): List<UserResult>? {
        if (roleId == null) {
            return userMapper.userToUserResultList(userRepository.findAll())
        }

        return userMapper.userToUserResultList(userRepository.findByRoleListContains(Role(roleId)).orElse(null))
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): UserResult? {
        val user = userRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the id: %s not found!", id))

        return userMapper.userToUserResult(user)
    }

    @Throws(NoSuchElementException::class)
    override fun findByEmail(email: String): UserResult? {
        val user = userRepository.findByEmail(email).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the email: %s not found!", email))

        return userMapper.userToUserResult(user)
    }

    override fun findByIdCard(idCard: String): UserResult? {
        val user = userRepository.findByIdCard(idCard).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the idCard: %s not found!", idCard))

        return userMapper.userToUserResult(user)
    }

    override fun findByIdAndRole(id: Long, role: Role): UserResult? {
        val user = userRepository.findByIdAndRoleListContains(id, role).orElse(null)
            ?: throw NoSuchElementException("The Client with the id: $id and rol: $role not found!")

        return userMapper.userToUserResult(user)
    }

    @Transactional
    override fun create(userInput: UserInput): UserResult? {
        val user = userMapper.userInputToUser(userInput)
        val savedUser = userRepository.saveAndFlush(user)

        entityManager.refresh(savedUser)

        return userMapper.userToUserResult(savedUser)
    }

    @Throws(NoSuchElementException::class)
    override fun update(userInput: UserInput): UserResult? {
        val user = userRepository.findById(userInput.id!!).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the id: %s not found!", userInput.id))

        userMapper.userInputToUser(userInput, user)

        return userMapper.userToUserResult(userRepository.save(user))
    }
}


@Service
class AbstractServiceService(
    @Autowired
    val serviceRepository: ServiceRepository,
    @Autowired
    val serviceMapper: ServiceMapper,
    @Autowired
    val entityManager: EntityManager,
) : ServiceService {


    override fun findAll(): List<ServiceDetails>? {
        return serviceMapper.serviceListToServiceListDetails(serviceRepository.findAll())
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): ServiceDetails? {
        val service = serviceRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException(String.format("The Service with the id: %s not found!", id))

        return serviceMapper.serviceToServiceDetails(service)
    }
}

@Service
@org.springframework.transaction.annotation.Transactional
class AppUserDetailsService(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val roleRepository: RoleRepository,
    @Autowired
    val roleMapper: RoleMapper,
) : UserDetailsService {

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the `UserDetails`
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never `null`)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     * GrantedAuthority
     */
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        var userAuth: org.springframework.security.core.userdetails.User
        val user: User = userRepository.findByEmail(username).orElse(null)
            ?: return org.springframework.security.core.userdetails.User(
                "", "", true, true, true, true,
                getAuthorities(
                    Arrays.asList(
                    roleRepository.findByName("ROLE_USER").get())))

        userAuth = org.springframework.security.core.userdetails.User(
            user.email, user.password, user.enabled, true, true,
            true, getAuthorities(user.roleList!!.toMutableList()))

        return userAuth
    }

    private fun getAuthorities(
        roles: MutableList<Role>,
    ): Collection<GrantedAuthority?>? {
        return getGrantedAuthorities(getPrivileges(roles))
    }

    private fun getPrivileges(roles: MutableList<Role>?): List<String> {
        val privileges: MutableList<String> = ArrayList()
        val collection: MutableList<Privilege> = ArrayList()
        if (roles != null) {
            for (role in roles) {
                collection.addAll(role.privilegeList!!)
            }
        }
        for (item in collection) {
            privileges.add(item.name)
        }
        return privileges
    }

    private fun getGrantedAuthorities(privileges: List<String>): List<GrantedAuthority?>? {
        val authorities: MutableList<GrantedAuthority?> = ArrayList()
        for (privilege in privileges) {
            authorities.add(SimpleGrantedAuthority(privilege))
        }
        return authorities
    }

}