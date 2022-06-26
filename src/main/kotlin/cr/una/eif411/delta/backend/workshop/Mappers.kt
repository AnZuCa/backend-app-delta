package cr.una.eif411.delta.backend.workshop

import org.mapstruct.*
import java.time.LocalDateTime



@Mapper(
    imports = [LocalDateTime::class],
    componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface RepairMapper {
    fun repairToRepairResult(
        repair: Repair,
    ): RepairResult

    fun repairListToRepairListResult(
        repairList: List<Repair>,
    ): List<RepairResult>

    @Mapping(target = "registrationDate", defaultExpression = "java(new java.util.Date())")
    fun repairInputToRepair(
        repairInput: RepairInput,
    ): Repair

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun repairInputToRepair(dto: RepairInput, @MappingTarget repair: Repair)
}


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface RoleMapper {
    fun roleListToRoleDetailsList(
        roleList: Set<Role>?,
    ): Set<RoleDetails>
}

@Mapper(
    imports = [LocalDateTime::class],
    componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface UserMapper {

    fun userToUserResult(
        user: User,
    ): UserResult

    fun userToUserRepairResult(
        user: User,
    ): UserRepairResult

    fun userToUserResultList(
        userResultList: List<User>,
    ): List<UserResult>

    @Mapping(target = "createDate", defaultExpression = "java(new java.util.Date())")
    fun userInputToUser(
        userInput: UserInput,
    ): User

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun userInputToUser(dto: UserInput, @MappingTarget user: User)
}

@Mapper(
    componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface VehicleMapper {
    fun vehicleToVehicleDetails(
        vehicle: Vehicle,
    ): VehicleDetails

    fun vehicleDetailsToVehicle(
        vehicle: VehicleDetails,
    ): Vehicle

    fun vehicleListToVehicleListDetails(
        vehicleList: List<Vehicle>,
    ): List<VehicleDetails>

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun vehicleDetailsToVehicle(dto: VehicleDetails, @MappingTarget vehicle: Vehicle)
}

@Mapper(
    componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface VehicleImageMapper {
    fun vehicleImageToVehicleImageDetails(
        vehicleImage: VehicleImage,
    ): VehicleImageDetails

    fun vehicleImageDetailsToVehicleImage(
        vehicleImageDetails: VehicleImageDetails,
    ): VehicleImage

    fun vehicleImageListToVehicleImageListDetails(
        vehicleImageList: List<VehicleImage>,
    ): List<VehicleImageDetails>

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun vehicleImageDetailsToVehicleImage(dto: VehicleDetails, @MappingTarget vehicle: Vehicle)
}

@Mapper(
    componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface ServiceMapper {
    fun serviceToServiceDetails(
        service: Service,
    ): ServiceDetails

    fun serviceListToServiceListDetails(
        serviceList: List<Service>,
    ): List<ServiceDetails>
}

@Mapper(
    componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface StatusMapper {
    fun statusToStatusDetails(
        status: Status,
    ): StatusDetails

}