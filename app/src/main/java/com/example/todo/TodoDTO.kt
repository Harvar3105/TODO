package com.example.todo

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDateTime
import java.time.ZoneOffset

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
        creationDate = parcel.readLong().let {
            if (it != -1L) LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) else null
        }
        date = parcel.readLong().let {
            if (it != -1L) LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) else null
        }
        isCompleted = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeLong(creationDate?.toEpochSecond(ZoneOffset.UTC) ?: -1L)
        parcel.writeLong(date?.toEpochSecond(ZoneOffset.UTC) ?: -1L)
        parcel.writeByte(if (isCompleted) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "$name: $description"
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