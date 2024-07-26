package com.example.todo

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

import java.util.Date
import java.util.UUID

class TodoDTO(
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    var creationDate: LocalDateTime? = null,
    var date: LocalDateTime? = null,
    var isCompleted: Boolean = false
) : Parcelable {

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        name = parcel.readString()
        description = parcel.readString()
        creationDate = LocalDateTime.parse(parcel.readString())
        date = LocalDateTime.parse(parcel.readString())
//        creationDate = parcel.readLong().takeIf { it != -1L }?.let {
//            LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
//        }
//        date = parcel.readLong().takeIf { it != -1L }?.let {
//            LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
//        }
        isCompleted = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        if (id == null) parcel.writeLong(0) else parcel.writeLong(id!!)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(creationDate.toString())
        parcel.writeString(date.toString())
//        parcel.writeLong(creationDate?.toEpochSecond(ZoneOffset.UTC) ?: -1L)
//        parcel.writeLong(date?.toEpochSecond(ZoneOffset.UTC) ?: -1L)
        parcel.writeByte(if (isCompleted) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        if (isCompleted){
            return "\n\tCompleted: $name, $description. \n\nDate created: ${creationDate?.format(
                DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm"))} \nDate estimated: ${date?.format(
                DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm"))}"
        } else {
            return "\n\tNot Completed: $name, $description. \n\nDate created: ${creationDate?.format(
                DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm"))} \nDate estimated: ${date?.format(
                DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm"))}"
        }
    }


    companion object CREATOR : Parcelable.Creator<TodoDTO> {
        override fun createFromParcel(parcel: Parcel): TodoDTO {
            return TodoDTO(parcel)
        }

        override fun newArray(size: Int): Array<TodoDTO?> {
            return arrayOfNulls(size)
        }

        fun toDTO(obj: Todo): TodoDTO {
            return TodoDTO(
                id = obj.id,
                name = obj.name,
                description = obj.description,
                creationDate = obj.creationDate,
                date = obj.date,
                isCompleted = obj.isCompleted
            )
        }

        fun fromDTO(dto: TodoDTO) : Todo {
            return Todo(
                id = dto.id,
                name = dto.name,
                description = dto.description,
                creationDate = dto.creationDate,
                date = dto.date,
                isCompleted = dto.isCompleted
            )
        }
    }
}