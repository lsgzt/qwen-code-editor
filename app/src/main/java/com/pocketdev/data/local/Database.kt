package com.pocketdev.data.local

import androidx.room.*
import com.pocketdev.domain.model.Language
import com.pocketdev.domain.model.Project
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Project operations
 */
@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects ORDER BY modifiedAt DESC")
    fun getAllProjects(): Flow<List<Project>>

    @Query("SELECT * FROM projects WHERE id = :projectId")
    suspend fun getProjectById(projectId: Long): Project?

    @Query("SELECT * FROM projects WHERE language = :language ORDER BY modifiedAt DESC")
    fun getProjectsByLanguage(language: Language): Flow<List<Project>>

    @Query("SELECT * FROM projects WHERE name LIKE '%' || :query || '%' ORDER BY modifiedAt DESC")
    fun searchProjects(query: String): Flow<List<Project>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: Project): Long

    @Update
    suspend fun updateProject(project: Project)

    @Delete
    suspend fun deleteProject(project: Project)

    @Query("DELETE FROM projects WHERE id = :projectId")
    suspend fun deleteProjectById(projectId: Long)

    @Query("UPDATE projects SET isFavorite = :isFavorite WHERE id = :projectId")
    suspend fun toggleFavorite(projectId: Long, isFavorite: Boolean)
}

/**
 * Database for storing projects
 */
@Database(entities = [Project::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao

    companion object {
        const val DATABASE_NAME = "pocketdev_database"
    }
}
