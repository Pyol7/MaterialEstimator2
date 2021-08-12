package com.example.materialestimator.utilities

import com.example.materialestimator.storage.local.entities.*
import com.example.materialestimator.storage.local.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class SeedDataProject {
    companion object {
        fun insert(instance: AppDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                // Note: The sequence of insertion affects the foreign key constraints
                projects.forEach {
                    instance.projectDao().insert(it)
                }
                tasks.forEach {
                    instance.taskDao().insert(it)
                }
                employees.forEach {
                    instance.employeeDao().insert(it)
                }
                tools.forEach {
                    instance.toolDao().insert(it)
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
                image = "levy",
                taskId_fk = 1
            ),
            Employee(
                name = "Justin Romero",
                image = "justin",
                taskId_fk = 1
            ),
            Employee(
                name = "Eva Romero",
                image = "eva",
                taskId_fk = 2
            ),
            Employee(
                name = "Jeffrey Romero",
                image = "jeff",
                taskId_fk = 2
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
                projectId_fk = 1
            ),
            Task(
                title = "Excavate pile cap",
                description = "14ft long by 4ft wide by 4ft deep",
                startDate = Date(),
                estimatedDays = 2,
                estimatedHours = 2,
                completionDate = Date(),
                projectId_fk = 1
            )
        )
        val tools = arrayListOf(
            Tool(
                type = "Screwgun",
                make = "Dewalt 5300rpm",
                image = "dewalt_screwgun",
                taskId_fk = 1
            ),
            Tool(
                type = "Screwgun",
                make = "Makita 6000rpm",
                image = "makita_screwgun",
                taskId_fk = 1
            ),
            Tool(
                type = "Demolition Hammer",
                make = "Total 2200W",
                image = "total_demo_hammer",
                taskId_fk = 1
            ),
            Tool(
                type = "Demolition Hammer",
                make = "Makita H1101C",
                image = "makita_demo_hammer",
                taskId_fk = 2
            ),
            Tool(
                type = "Hammer Drill",
                make = "milwaukee 5262-21",
                image = "milwaukee_hammer_drill",
                taskId_fk = 2
            ),
            Tool(
                type = "Circular Saw",
                make = "Dewalt 7",
                image = "dewalt_circular_saw",
                taskId_fk = 2
            ),
            Tool(
                type = "Level",
                make = "Johnson 48",
                image = "johnson_level"
            ),
            Tool(
                type = "Extension Cord",
                make = "50ft yellow",
                image = "extension_cord_yellow"
            ),
            Tool(
                type = "Shears",
                make = "Irwin red",
                image = "irwin_shears"
            ),
            Tool(
                type = "Hammer",
                make = "Stanley 20oz",
                image = "stanley_hammer"
            )
        )

    }
}