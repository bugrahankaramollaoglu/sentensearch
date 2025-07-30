package com.bugrahankaramollaoglu.sentensearch.Helpers

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.view.View
import androidx.cardview.widget.CardView
import com.bugrahankaramollaoglu.sentensearch.R
import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentSearchBinding

class FlipHelper(private val context: Context, view: View) {
    private var isAnimating = false
    var isFront = true
    private val scale = context.resources.displayMetrics.density
    private var front: CardView
    private var back: CardView
    private val frontAnimation: AnimatorSet
    private val backAnimation: AnimatorSet

    init {
        front = view.findViewById(R.id.definitionCard)
        back = view.findViewById(R.id.backCard)
        front.cameraDistance = 8000 * scale
        back.cameraDistance = 8000 * scale
        frontAnimation =
            AnimatorInflater.loadAnimator(context, R.animator.front_animator) as AnimatorSet
        backAnimation =
            AnimatorInflater.loadAnimator(context, R.animator.back_animator) as AnimatorSet
    }

    fun flipCards(binding: FragmentSearchBinding) {
        if (!isAnimating) {
            isAnimating = true
            if (isFront) {
                frontAnimation.setTarget(front)
                backAnimation.setTarget(back)
                frontAnimation.start()
                backAnimation.start()
                binding.backCard.visibility = View.VISIBLE
                isFront = false
            } else {
                frontAnimation.setTarget(back)
                backAnimation.setTarget(front)
                backAnimation.start()
                frontAnimation.start()
                isFront = true
            }
            // Add a listener to reset isAnimating after the animation completes
            frontAnimation.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    isAnimating = false
                }

                override fun onAnimationCancel(animation: Animator) {
                    isAnimating = false
                }

                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
    }
}
