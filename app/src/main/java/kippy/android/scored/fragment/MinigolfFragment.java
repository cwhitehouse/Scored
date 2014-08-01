package kippy.android.scored.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

	int mCurrentRound = 0;

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
		for(int i=0 ; i<1 ; i++) {
			MinigolfHoleEntry minigolfHoleEntry = MinigolfHoleEntry.inflate(getMyActivity(), vHoles, i%2 == 0);
			minigolfHoleEntry.layout(i+1);

			vHoles.addView(minigolfHoleEntry.getView());
		}

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
		}
	}

	//================================================================================
	// Round Management
	//================================================================================

	public void checkScores() {
		
	}

}
