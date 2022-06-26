package cr.una.eif411.delta.backend.workshop

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${url.repairs}")
class RepairController(private val repairService: RepairService) {
    @GetMapping
    @ResponseBody
    fun findAll() = repairService.findAll()

    @Throws(NoSuchElementException::class)
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = repairService.findById(id)

    @Throws(NoSuchElementException::class)
    @GetMapping("mechanic/{id}")
    @ResponseBody
    fun findByMechanicId(@PathVariable id: Long) = repairService.findByMechanicId(id)

    @Throws(NoSuchElementException::class)
    @GetMapping("client/{id}")
    @ResponseBody
    fun findByClientId(@PathVariable id: Long) = repairService.findByClientId(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody repairInput: RepairInput): RepairResult? {
        return repairService.create(repairInput)
    }

    @Throws(NoSuchElementException::class)
    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody repairInput: RepairInput): RepairResult? {
        return repairService.update(repairInput)
    }
}

@RestController
@RequestMapping("\${url.vehicles}")
class VehicleController(private val vehicleService: VehicleService) {
    @GetMapping
    @ResponseBody
    fun findAll() = vehicleService.findAll()

    @Throws(NoSuchElementException::class)
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = vehicleService.findById(id)

    @Throws(NoSuchElementException::class)
    @RequestMapping(value = ["/",""], method = [RequestMethod.GET],  params = ["plate_number"])
    @ResponseBody
    fun findByPlateNumber(@RequestParam("plate_number") plateNumber: String) = vehicleService.findByPlateNumber(plateNumber)

    @Throws(NoSuchElementException::class)
    @RequestMapping(value = ["/",""], method = [RequestMethod.GET],  params = ["vin_number"])
    @ResponseBody
    fun findByVinNumber(@RequestParam("vin_number") vinNumber: String) = vehicleService.findByVinNumber(vinNumber)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody vehicleInput: VehicleDetails): VehicleDetails? {
        return vehicleService.create(vehicleInput)
    }

    @Throws(NoSuchElementException::class)
    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody vehicleInput: VehicleDetails): VehicleDetails? {
        return vehicleService.update(vehicleInput)
    }
}

@RestController
@RequestMapping("\${url.user}")
class UserController(private val userService: UserService, private val repairService: RepairService) {

    @GetMapping
    @ResponseBody
    fun findAll(@RequestParam(name = "role_id", required = false) roleId: Long?) = userService.findAll(roleId)

    @Throws(NoSuchElementException::class)
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = userService.findById(id)

    @Throws(NoSuchElementException::class)
    @RequestMapping(value = ["/",""], method = [RequestMethod.GET],  params = ["email"])
    @ResponseBody
    fun findByEmail(@RequestParam("email") email: String) = userService.findByEmail(email)

    @Throws(NoSuchElementException::class)
    @RequestMapping(value = ["/",""], method = [RequestMethod.GET],  params = ["idCard"])
    @ResponseBody
    fun findByIdCard(@RequestParam("idCard") idCard: String) = userService.findByIdCard(idCard)


    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody userInput: UserInput): UserResult? {
        return userService.create(userInput)
    }

    @Throws(NoSuchElementException::class)
    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody userInput: UserInput): UserResult? {
        return userService.update(userInput)
    }

    @Throws(NoSuchElementException::class)
    @GetMapping("/{id}/repairs")
    @ResponseBody
    fun getMechanicRepairs(@PathVariable id: Long) = repairService.findByMechanicId(id)
}



@RestController
@RequestMapping("\${url.service}")
class ServiceController(private val serviceService: ServiceService) {

    @GetMapping
    @ResponseBody
    fun findAll() = serviceService.findAll()

    @Throws(NoSuchElementException::class)
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = serviceService.findById(id)

}