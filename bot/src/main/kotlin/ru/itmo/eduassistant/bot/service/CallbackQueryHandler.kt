package ru.itmo.eduassistant.bot.service

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import ru.itmo.eduassistant.bot.enm.CallbackType
import ru.itmo.eduassistant.bot.service.state.student.StudentInChannelViewState
import ru.itmo.eduassistant.bot.service.state.student.StudentInCreatingQuestionState
import ru.itmo.eduassistant.bot.service.state.student.StudentInQuestionViewState
import ru.itmo.eduassistant.bot.service.state.student.StudentInQueueViewState
import ru.itmo.eduassistant.bot.service.state.teacher.TeacherInChannelViewState
import ru.itmo.eduassistant.bot.service.state.teacher.TeacherInCreatingQueueState
import ru.itmo.eduassistant.bot.service.state.teacher.TeacherInQuestionViewState
import ru.itmo.eduassistant.bot.service.state.teacher.TeacherInQueueViewState
import ru.itmo.eduassistant.bot.util.CallbackUtils

@Service
class CallbackQueryHandler(private val stateSwitcher: StateSwitcher, private val callbackUtils: CallbackUtils) {

    fun handleCallbackQuery(callbackQuery: CallbackQuery): BotApiMethod<*> {
        val callbackType = callbackUtils.parseCallbackData(callbackQuery.data).callbackType
        return stateSwitcher.switchStateFromCallback(
            callbackQuery, when (callbackType) {
                CallbackType.TEACHER_IN_MAIN_MENU_STATE_QUESTION_CHOICE -> TeacherInQuestionViewState::class
                CallbackType.TEACHER_IN_QUEUE_MENU_STATE_QUEUE_CHOICE -> TeacherInQueueViewState::class
                CallbackType.TEACHER_IN_CREATING_QUEUE_STATE_CHANNEL_CHOICE -> TeacherInCreatingQueueState::class
                CallbackType.TEACHER_IN_CHANNEL_MENU_STATE_CHANNEL_CHOICE -> TeacherInChannelViewState::class

                CallbackType.STUDENT_IN_MAIN_MENU_STATE_CHANNEL_CHOICE -> StudentInChannelViewState::class
                CallbackType.STUDENT_IN_MAIN_MENU_STATE_QUEUE_CHOICE -> StudentInQueueViewState::class
                CallbackType.STUDENT_IN_CHANNEL_MENU_STATE_CHANNEL_CHOICE -> StudentInChannelViewState::class
                CallbackType.STUDENT_IN_QUESTION_MENU_STATE_QUESTION_CHOICE -> StudentInQuestionViewState::class
                CallbackType.STUDENT_IN_QUESTION_MENU_STATE_CHANNEL_CHOICE -> StudentInCreatingQuestionState::class
            }
        )
    }
}