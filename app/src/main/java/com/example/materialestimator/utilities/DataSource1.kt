package com.example.materialestimator.utilities

import com.example.materialestimator.models.entities.Employee
import com.example.materialestimator.models.entities.Project
import com.example.materialestimator.models.entities.Task
import com.example.materialestimator.models.materials.*
import com.example.materialestimator.storage.local.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DataSource1 {
    companion object {

        fun insertProjectsAndTasks(instance: AppDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                projects.forEach {
                    instance.projectDao().insert(it)
                }
                employees.forEach {
                    instance.employeeDao().insert(it)
                }
                tasks.forEach {
                    instance.taskDao().insert(it)
                }
            }
        }

        private val projects = arrayListOf(
            Project(name = "108 Ridge Road Maraval", image = "project_1"),
            Project(name = "54 Lange Park Chaguanas", image = "project_2"),
            Project(name = "23 Main Road Tobago", image = "project_3"),
            Project(name = "Anglais Road Cumana", image = "project_4"),
            Project(name = "La Riviera Apt 1K", image = "project_5"),
            Project(name = "1/4 mm Toco Road Toco", image = "project_6")
        )

        private val employees = arrayListOf(
            Employee(
                name = "Levy Romero",
                image = "levy"
            ),
            Employee(
                name = "Justin Romero",
                image = "justin"
            ),
            Employee(
                name = "Eva Romero",
                image = "eva"
            )
        )

        private val tasks = arrayListOf(
            Task(
                title = "Protect site",
                description = "Lay polythene over floor followed by 1/4\" plywood",
                startDate = Date(),
                estimatedDays = 1,
                estimatedHours = 6,
                completionDate = Date(),
                employees = arrayListOf(
                    Employee(
                        name = "Levy Romero",
                        image = "levy"
                    ),
                    Employee(
                        name = "Justin Romero",
                        image = "justin"
                    ),
                    Employee(
                        name = "Eva Romero",
                        image = "eva"
                    )
                ),
                materials = arrayListOf(
                    Panel(
                        name = "Ultra Light Gypsum Panel",
                        unitprice = 64.00,
                        length = 8.0,
                        width = 4.0,
                        image = "ultralight_gypsum_panel",
                        categoryid = 1
                    ),
                    Furring(
                        name = "Furring Channel",
                        unitprice = 15.00,
                        length = 12.0,
                        image = "furring_channel",
                        categoryid = 1
                    ),
                    Screw(
                        name = "Drywall Screw 1-1/4",
                        unitprice = 0.15,
                        coverage = 40.0,
                        image = "drywall_screw",
                        categoryid = 1
                    )
                ),
                projectID = 1
            ),

            Task(
                title = "Excavate pile cap",
                description = "14ft long by 4ft wide by 4ft deep",
                startDate = Date(),
                estimatedDays = 2,
                estimatedHours = 2,
                completionDate = Date(),
                employees = arrayListOf(
                    Employee(
                        name = "Justin Romero",
                        image = "justin"
                    ),
                    Employee(
                        name = "Eva Romero",
                        image = "eva"
                    )
                ),
                materials = arrayListOf(
                    CChannel(
                        name = "C Channel",
                        unitprice = 50.0,
                        length = 16.0,
                        image = "gypsum_c_channel",
                        categoryid = 1
                    ),
                    JointCompound(
                        name = "Joint Compound",
                        unitprice = 50.0,
                        coverage = 150.0,
                        image = "all_purpose_joint_compound",
                        categoryid = 1
                    ),
                    Tee(
                        name = "Main Tee",
                        unitprice = 25.0,
                        length = 12.0,
                        image = "cross_tee",
                        categoryid = 1
                    ),
                    WallAngle(
                        name = "Wall Angle",
                        unitprice = 8.0,
                        length = 10.0,
                        image = "gypsum_ceiling_wall_angle",
                        categoryid = 1
                    )
                ),
                projectID = 1
            )
        )


    }
}