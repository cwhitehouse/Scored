package kippy.android.scored.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import kippy.android.scored.R;
import kippy.android.scored.stub.AvatarStub;
import kippy.android.scored.stub.minigolf.MinigolfHoleEntry;
import kippy.android.scored.stub.minigolf.MinigolfPlayerScore;
import kippy.android.scored.ui.view.MyScrollView;
import kippy.android.scored.util.MathUtils;

/**
 * Created by christianwhitehouse on 7/30/14.
 */
public class MinigolfFragment extends MyFragment {

	//================================================================================
	// Constants
	//================================================================================

	private static final int NUM_HOLES = 18;
	private static final float SHADOW_MAX_ALPHA = 0.1f;

	String[] mPlayers = {"KIP","TOM","AAC","EBG"};

	//================================================================================
	// Variables
	//================================================================================

	LinearLayout vAvatars;

	View vShadow;
	MyScrollView vScroll;

	LinearLayout vHoles;

	LinearLayout vPlayers;
	MinigolfPlayerScore[] vPlayerScores;

	int mCurrentHole = 0;

	int mScoringPlayer = -1;
	int mScoringRound = -1;

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
					vShadow.setAlpha(MathUtils.interpolateValue(t, 0, vShadow.getHeight(), 0, SHADOW_MAX_ALPHA));
			}
		});

		LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		vHoles = (LinearLayout) view.findViewById(R.id.minigolf_holes);
		inflateNextHole(false);

		mUserIDs = new ArrayList<Integer>();
		vAvatars = (LinearLayout) view.findViewById(R.id.minigolf_avatars);
		vPlayers = (LinearLayout) view.findViewById(R.id.minigolf_players);
		vPlayerScores = new MinigolfPlayerScore[mPlayers.length];
		for(int i=0 ; i<mPlayers.length ; i++) {
			ViewGroup avatarWrapper = (ViewGroup) layoutInflater.inflate(R.layout.my_frame_stretchable, vAvatars, false);
			vAvatars.addView(avatarWrapper);

			AvatarStub avatar = AvatarStub.inflate(getMyActivity(), avatarWrapper);
			avatarWrapper.addView(avatar.getView());

			MinigolfPlayerScore playerScore = MinigolfPlayerScore.inflate(this, vPlayers, avatar, i, NUM_HOLES);
			playerScore.getAvatar().layoutAvatar(null, mPlayers[i]);

			vPlayers.addView(playerScore.getView());
			vPlayerScores[i] = playerScore;

			mUserIDs.add(i);
		}

		mHandler.postDelayed(mSetScoreRunnable, ADD_SCORE_DELAY);
	}

	//================================================================================
	// Round Management
	//================================================================================

	public void checkScores() {
		int lowestScore = Integer.MAX_VALUE;
		for(MinigolfPlayerScore playerScore : vPlayerScores) {
			int score = playerScore.getScore(mCurrentHole);
			if(score < lowestScore)
				lowestScore = score;
		}

		if(lowestScore > 0) {
			for(MinigolfPlayerScore playerScore : vPlayerScores) {
				if(mCurrentHole < 17)
					playerScore.inflateNextHole();
				if(lowestScore > 1 && playerScore.getScore(mCurrentHole) == lowestScore)
					playerScore.highlightScore(mCurrentHole);
			}

			mUserIDs.clear();
			for(int i=0 ; i<mPlayers.length ; i++)
				mUserIDs.add(i);

			mCurrentHole++;
			if(mCurrentHole < 18)
				inflateNextHole(true);
		}
	}

	public void inflateNextHole(boolean animated) {
		MinigolfHoleEntry minigolfHoleEntry = MinigolfHoleEntry.inflate(getMyActivity(), vHoles, mCurrentHole%2 == 0);
		minigolfHoleEntry.layout(mCurrentHole+1);

		vHoles.addView(minigolfHoleEntry.getView());

		if(animated) {
			minigolfHoleEntry.getView().setScaleY(0f);
			minigolfHoleEntry.getView().animate().scaleY(1f).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					vScroll.smoothScrollTo(0,Integer.MAX_VALUE);
				}
			});
		}
	}

	//================================================================================
	// Auto Score File
	//================================================================================

	private static final long ADD_SCORE_DELAY = 250l;

	private ArrayList<Integer> mUserIDs;
	private Handler mHandler = new Handler();

	private Runnable mSetScoreRunnable = new Runnable() {
		@Override
		public void run() {
			int randIndex = MathUtils.randomInt(0,mUserIDs.size()-1);
			int userID = mUserIDs.remove(randIndex);

			vPlayerScores[userID].setScore(mCurrentHole, generateRandomScore());
			checkScores();

			if(mCurrentHole < 18)
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
