package com.example.todo

import android.os.Parcel
import android.os.Parcelable

import java.util.Date
import java.util.UUID

class TodoDTO(
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    var creationDate: Date? = null,
    var date: Date? = null,
    var isCompleted: Boolean = false
) : Parcelable {

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        name = parcel.readString()
        description = parcel.readString()
        creationDate = Date(parcel.readLong())
        date = Date(parcel.readLong())
        isCompleted = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeLong(creationDate?.time ?: 0)
        parcel.writeLong(date?.time ?: 0)
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