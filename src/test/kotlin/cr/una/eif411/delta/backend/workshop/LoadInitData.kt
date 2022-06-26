package cr.una.eif411.delta.backend.workshop

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql

@SpringBootTest
@Sql(
    "/import-roles.sql",
    "/import-privileges.sql",
    "/import-users.sql",
    "/import-vehicles.sql",
    "/import-services.sql",
    "/import-status.sql",
    "/import-repairs.sql",
    "/import-repair-service.sql",
    "/import-user-roles.sql",
)
class LoadInitData(
    @Autowired
    val repairRepository: RepairRepository,
    @Autowired
    val vehicleRepository: VehicleRepository,
    @Autowired
    val serviceRepository: ServiceRepository,
    @Autowired
    val userRepository: UserRepository,
) {
    @Test
    fun `check number of repairs`() {
        val repairList: List<Repair> = repairRepository.findAll()
        Assertions.assertTrue(repairList.size == 3)
    }

    @Test
    fun `check number of vehicles`() {
        val vehicleList: List<Vehicle> = vehicleRepository.findAll()
        Assertions.assertTrue(vehicleList.size == 3)
    }

    @Test
    fun `check number of services`() {
        val serviceList: List<Service> = serviceRepository.findAll()
        Assertions.assertTrue(serviceList.size == 3)
    }

    @Test
    fun `check number of services in the repair with id 1`() {
        val repair: Repair = repairRepository.getById(1)
        Assertions.assertTrue( repair.serviceList.size == 2)
    }

    @Test
    fun `check number of users`() {
        val userList: List<User> = userRepository.findAll()
        Assertions.assertTrue(userList.size == 4)
    }
}