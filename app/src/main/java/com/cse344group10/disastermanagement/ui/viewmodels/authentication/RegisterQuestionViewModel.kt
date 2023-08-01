package com.cse344group10.disastermanagement.ui.viewmodels.authentication

import androidx.compose.ui.geometry.Size
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cse344group10.disastermanagement.models.user.User

class RegisterQuestionViewModel: ViewModel() {
    val userAnswer = MutableLiveData("")
    val userNotes = MutableLiveData("")
    val questionDropDownExpanded = MutableLiveData(false)
    val selectedQuestion = MutableLiveData("")
    val textFilledSize = MutableLiveData(Size.Zero)
    val questionList = arrayOf(
        User.SecurityQuestion.Pet.question,
        User.SecurityQuestion.BookMovie.question,
        User.SecurityQuestion.City.question,
        User.SecurityQuestion.Mother.question,
        User.SecurityQuestion.Vacation.question
    )
    val questionError = MutableLiveData(false)
    val answerError = MutableLiveData(false)

    fun checkFields(): Boolean {
        if (selectedQuestion.value == "") {
            questionError.value = true
        }
        if (userAnswer.value!!.trim() == "") {
            answerError.value = true
        }
        return (!questionError.value!! && !answerError.value!!)
    }

    fun updateTextFilledSize(size: Size) {
        textFilledSize.value = size
    }

    fun updateSelectedQuestion(question: String) {
        questionError.value = false
        selectedQuestion.value = question
    }

    fun updateDropDownState(state: Boolean) {
        questionDropDownExpanded.value = state
    }

    fun updateUserAnswer(answer: String) {
        answerError.value = false
        userAnswer.value = answer
    }

    fun updateUserNotes(notes: String) {
        userNotes.value = notes
    }
}