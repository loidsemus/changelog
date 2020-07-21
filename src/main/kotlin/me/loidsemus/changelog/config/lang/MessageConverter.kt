package me.loidsemus.changelog.config.lang

import de.exlll.configlib.Converter

class MessageConverter : Converter<Message, String> {

    override fun convertFrom(element: String, info: Converter.ConversionInfo) = Message(element)

    override fun convertTo(element: Message, info: Converter.ConversionInfo) = element.text
}