package cr.una.eif411.delta.backend.workshop

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RepairRepository : JpaRepository<Repair, Long> {
    fun findByMechanicId(mechanicId: Long): Optional<List<Repair>>
    fun findByClientId(clientId: Long): Optional<List<Repair>>
}

@Repository
interface VehicleRepository : JpaRepository<Vehicle, Long>{
    fun findByPlateNumber(plateNumber: String?): Optional<Vehicle>
    fun findByVinNumber(vinNumber: String?): Optional<Vehicle>
}

@Repository
interface ServiceRepository : JpaRepository<Service, Long>

@Repository
interface UserRepository : JpaRepository<User, Long>
{
    fun findByEmail(email : String) : Optional<User>
    fun findByIdCard(idCard : String) : Optional<User>
    fun findByIdAndRoleListContains(id : Long, role : Role) : Optional<User>
    fun findByRoleListContains(role : Role) : Optional<List<User>>
}

@Repository
interface RoleRepository : JpaRepository<Role, Long>
{
    fun findByName(name : String) : Optional<Role>
}