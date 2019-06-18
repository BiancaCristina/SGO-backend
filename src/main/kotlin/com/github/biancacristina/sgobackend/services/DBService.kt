package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.*
import com.github.biancacristina.sgobackend.domain.enums.Status
import com.github.biancacristina.sgobackend.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class DBService {

    @Autowired
    private lateinit var cityRepository: CityRepository

    @Autowired
    private lateinit var stateRepository: StateRepository

    @Autowired
    private lateinit var typeOfLaborRepository: TypeOfLaborRepository

    @Autowired
    private lateinit var laborRepository: LaborRepository

    @Autowired
    private lateinit var typeOfCostAggregationRepository: TypeOfCostAggregationRepository

    @Autowired
    private lateinit var costAggregationRepository: CostAggregationRepository

    @Autowired
    private lateinit var projectRepository: ProjectRepository

    fun instantiateTestDataBase(): Unit {
        var e1 = State(0, "Minas Gerais")
        var e2 = State(0, "Rio de Janeiro")
        var e3 = State(0, "São Paulo")

        stateRepository.saveAll(Arrays.asList(e1,e2,e3))

        var c1 = City(0, "Uberlândia", e1)
        var c2 = City(0, "Belo Horizonte", e1)
        var c3 = City(0, "São Paulo", e3)

        cityRepository.saveAll(Arrays.asList(c1,c2,c3))
        stateRepository.saveAll(Arrays.asList(e1,e2,e3))

        var tca1 = TypeOfCostAggregation(0, "Ag 1")
        var tca2 = TypeOfCostAggregation(0, "Ag 2")

        typeOfCostAggregationRepository.saveAll(Arrays.asList(tca1, tca2))

        var ca1 = CostAggregation(
                0,
                "Concessão",
                tca1
            )

        var ca2 = CostAggregation(
                0,
                "Expansão",
                tca1
        )

        costAggregationRepository.saveAll(Arrays.asList(ca1,ca2))

        var tl1 = TypeOfLabor(0, "Pequena")
        var tl2 = TypeOfLabor(0, "Média")
        var tl3 = TypeOfLabor(0, "Grande")

        typeOfLaborRepository.saveAll(Arrays.asList(tl1,tl2,tl3))


        var str_Date = "09/01/1999 12:01"
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        var date = LocalDateTime.parse(str_Date, formatter)

        var str_Date2 = "24/11/2010 07:14"
        var date2 = LocalDateTime.parse(str_Date2, formatter)

        var p1 = Project(
                0,
                4854.21,
                211000.43,
                654987.12,
                5111234.90,
                4433423.45,
                date,
                date2,
                c1,
                Status.ACIONADO
        )

        projectRepository.save(p1)

        var l1 = Labor(0,
                "Labor 1",
                123.12,
                678.78,
                988.90,
                43.10,
                54.11,
                tl1,
                ca1,
                p1)

        var l2 = Labor(0,
                "Labor 2",
                454.21,
                1000.43,
                987.12,
                534.90,
                745645.45,
                tl2,
                ca2,
                p1)

        costAggregationRepository.saveAll(Arrays.asList(ca1,ca2))
        laborRepository.saveAll(Arrays.asList(l1,l2))
        typeOfLaborRepository.saveAll(Arrays.asList(tl1,tl2,tl3))

        var labors = mutableSetOf<Labor>()
        labors.add(l1)
        labors.add(l2)
        p1.labors.add(l1)
        p1.labors.add(l2)

        projectRepository.save(p1)

    }
}