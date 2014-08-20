package kippy.android.scored.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;

import kippy.android.scored.R;
import kippy.android.scored.activity.MinigolfScorePickerActivity;
import kippy.android.scored.stub.AvatarStub;
import kippy.android.scored.stub.minigolf.MinigolfPlayerScore;
import kippy.android.scored.stub.minigolf.MinigolfRoundEntry;
import kippy.android.scored.ui.view.MyScrollView;
import kippy.android.scored.util.MathUtils;

/**
 * Created by christianwhitehouse on 7/30/14.
 */
public class MinigolfFragment extends MyFragment {

	//================================================================================
	// Constants
	//================================================================================

	public static final int REQUEST_CODE_PICK_SCORE = 0;

	private static final int NUM_ROUNDS = 18;
	private static final float SHADOW_MAX_ALPHA = 0.1f;

	private static final long DELAY_UPDATE_SCORE = 150l;
	private static final long DELAY_CHECK_SCORES = 500l;
	private static final long DELAY_INFLATE_ROUND = 350l;

	String[] mPlayers = {"KIP","TOM","SEA","JBS"};

	//================================================================================
	// Variables
	//================================================================================

	LinearLayout vAvatars;

	View vShadow;
	MyScrollView vScroll;

	LinearLayout vRounds;
	MinigolfRoundEntry vRoundsTotal;

	LinearLayout vPlayers;
	MinigolfPlayerScore[] vPlayerScores;

	int mCurrentRound = 0;

	int mScoringPlayer = -1;
	int mScoringRound = -1;

	private Handler mHandler = new Handler();
	private Runnable mInflateRoundRunnable = new Runnable() {
		@Override
		public void run() {
			inflateNextRound(true);
		}
	};

	//================================================================================
	// Life Cycle Management
	//================================================================================

	@Override
	public int getLayoutID() {
		return R.layout.fragment_minigolf;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		vShadow = view.findViewById(R.id.minigolf_shadow);

		vScroll = (MyScrollView) view.findViewById(R.id.minigolf_scroll_view);
		vScroll.setOnScrollListener(new MyScrollView.OnScrollListener() {
			@Override
			public void onScrollChanged(MyScrollView scrollView, int l, int t, int oldl, int oldt) {
				if(t >= vShadow.getHeight())
					vShadow.setAlpha(SHADOW_MAX_ALPHA);
				else
					vShadow.setAlpha(MathUtils.interpolateValue(t, 0, vShadow.getHeight()/2, 0, SHADOW_MAX_ALPHA));
			}
		});

		LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		vRounds = (LinearLayout) view.findViewById(R.id.minigolf_rounds);
		vRoundsTotal = new MinigolfRoundEntry(getMyActivity(), vRounds, false);
		vRoundsTotal.layout("TOT");
		inflateNextRound(false);

		mUserIDs = new ArrayList<Integer>();
		vAvatars = (LinearLayout) view.findViewById(R.id.minigolf_avatars);
		vPlayers = (LinearLayout) view.findViewById(R.id.minigolf_players);
		vPlayerScores = new MinigolfPlayerScore[mPlayers.length];
		for(int i=0 ; i<mPlayers.length ; i++) {
			ViewGroup avatarWrapper = (ViewGroup) layoutInflater.inflate(R.layout.my_frame_stretchable, vAvatars, false);
			vAvatars.addView(avatarWrapper);

			AvatarStub avatar = AvatarStub.inflate(getMyActivity(), avatarWrapper);
			avatarWrapper.addView(avatar.getView());

			MinigolfPlayerScore playerScore = MinigolfPlayerScore.inflate(this, vPlayers, avatar, i, NUM_ROUNDS);
			playerScore.getAvatar().layoutAvatar(null, mPlayers[i]);

			vPlayers.addView(playerScore.getView());
			vPlayerScores[i] = playerScore;

			mUserIDs.add(i);
		}

		//mHandler.postDelayed(mSetScoreRunnable, ADD_SCORE_DELAY);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
		switch(requestCode) {
			case REQUEST_CODE_PICK_SCORE:
				if(resultCode == Activity.RESULT_OK) {
					int score = resultIntent.getIntExtra(MinigolfScorePickerActivity.EXTRA_SCORE, -1);
					if(score > 0) mHandler.postDelayed(new UpdateScoreRunnable(mScoringRound, score), DELAY_UPDATE_SCORE);
				}
				break;
			default:
				super.onActivityResult(requestCode, resultCode, resultIntent);
		}
	}

	//================================================================================
	// Round Management
	//================================================================================

	public void updateScoreForPlayer(int round, int player) {
		mScoringRound = round;
		mScoringPlayer = player;

		startActivityForResult(new Intent(getActivity(), MinigolfScorePickerActivity.class), REQUEST_CODE_PICK_SCORE);
	}

	private void updateScoreForRound(int round, int score) {
		if(getActivity() == null) return;

		vPlayerScores[mScoringPlayer].setScore(round, score);
		mHandler.postDelayed(new CheckScoresRunnable(round), DELAY_CHECK_SCORES);
	}

	private void checkScores(int round) {
		if(getActivity() == null) return;

		int lowestScore = Integer.MAX_VALUE;
		int highestScore = Integer.MIN_VALUE;
		for(MinigolfPlayerScore playerScore : vPlayerScores) {
			int score = playerScore.getScore(round);
			if(score < lowestScore)
				lowestScore = score;
			if(score > highestScore)
				highestScore = score;
		}

		if(lowestScore > 0) {
			for(MinigolfPlayerScore playerScore : vPlayerScores) {
				int score = playerScore.getScore(round);
				if(lowestScore > 1 && score == lowestScore && score < highestScore)
					playerScore.highlightScore(round);
				else
					playerScore.unHighlightScore(round);
			}

			mUserIDs.clear();
			for(int i=0 ; i<mPlayers.length ; i++)
				mUserIDs.add(i);

			if(round == mCurrentRound) {
				mCurrentRound++;
				if(mCurrentRound < 18) {
					if(lowestScore != 1)
						mHandler.postDelayed(mInflateRoundRunnable, DELAY_INFLATE_ROUND);
					else
						inflateNextRound(true);
				} else
					updateTotals();
			} else
				updateTotals();
		}
	}

	private void inflateNextRound(boolean animated) {
		if(getActivity() == null) return;

		MinigolfRoundEntry minigolfRoundEntry = MinigolfRoundEntry.inflate(getMyActivity(), vRounds, mCurrentRound % 2 == 0);
		minigolfRoundEntry.layout(mCurrentRound + 1);

		vRounds.addView(minigolfRoundEntry.getView(), mCurrentRound);

		if(animated) {
			minigolfRoundEntry.getView().setPivotY(0);
			minigolfRoundEntry.getView().setScaleY(0f);
			minigolfRoundEntry.getView().animate().scaleY(1f).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					vScroll.smoothScrollTo(0, Integer.MAX_VALUE);
				}
			}).setInterpolator(new DecelerateInterpolator());
		}

		if(mCurrentRound > 0) {
			for(MinigolfPlayerScore playerScore : vPlayerScores) {
				if(mCurrentRound < 18)
					playerScore.inflateNextRound();
			}
		}

		if(animated) {
			vRoundsTotal.getView().setTranslationY(-getResources().getDimensionPixelSize(R.dimen.minigolf_score_entry_size));
			vRoundsTotal.getView().animate().translationY(0f).setInterpolator(new DecelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					updateTotals();
				}
			});
		}
	}

	private void updateTotals() {
		int lowestScore = Integer.MAX_VALUE;
		int highestScore = Integer.MIN_VALUE;

		for(MinigolfPlayerScore playerScore : vPlayerScores) {
			int score = playerScore.getTotalScore();
			if(score < lowestScore)
				lowestScore = score;
			if(score > highestScore)
				highestScore = score;
		}

		for(MinigolfPlayerScore playerScore : vPlayerScores) {
			int score = playerScore.getTotalScore();
			playerScore.updateTotalScore(score, score <= lowestScore);
		}
	}

	//================================================================================
	// Delayed Stuff Handling
	//================================================================================

	private class UpdateScoreRunnable implements Runnable {
		int mRound;
		int mScore;
		private UpdateScoreRunnable(int round, int score) { mRound = round; mScore = score; }
		public void run() { updateScoreForRound(mRound, mScore); }
	}

	private class CheckScoresRunnable implements Runnable {
		int mRound;
		private CheckScoresRunnable(int round) { mRound = round; }
		public void run() { checkScores(mRound) ;}
	}

	//================================================================================
	// Auto Score File
	//================================================================================

	private static final long ADD_SCORE_DELAY = 1500l;

	private ArrayList<Integer> mUserIDs;

	private Runnable mSetScoreRunnable = new Runnable() {
		@Override
		public void run() {
			int randIndex = MathUtils.randomInt(0,mUserIDs.size()-1);
			int userID = mUserIDs.remove(randIndex);

			vPlayerScores[userID].setScore(mCurrentRound, generateRandomScore());
			checkScores(mCurrentRound);

			if(mCurrentRound < 18)
				mHandler.postDelayed(this, ADD_SCORE_DELAY);
		}
	};

	private int generateRandomScore() {
		int randInt = MathUtils.randomInt(0,1);
		if(randInt == 0)
			return MathUtils.randomInt(2,3);

		randInt = MathUtils.randomInt(1,45);
		if(randInt == 45)
			return 1;

		return MathUtils.randomInt(1,6);
	}

}
