package kippy.android.scored.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import kippy.android.scored.R;
import kippy.android.scored.stub.minigolf.MinigolfHoleEntry;
import kippy.android.scored.stub.minigolf.MinigolfPlayerScore;

/**
 * Created by christianwhitehouse on 7/30/14.
 */
public class MinigolfFragment extends MyFragment {

	private static final int NUM_HOLES = 18;

	String[] mPlayers = {"KIP","TOM","AAC","EBG"};

	LinearLayout vHoles;

	LinearLayout vPlayers;
	MinigolfPlayerScore[] vPlayerScores;

	@Override
	public int getLayoutID() {
		return R.layout.fragment_minigolf;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		vHoles = (LinearLayout) view.findViewById(R.id.minigolf_holes);
		for(int i=0 ; i<NUM_HOLES+1 ; i++) {
			MinigolfHoleEntry minigolfHoleEntry = MinigolfHoleEntry.inflate(getMyActivity(), vHoles, i%2 != 0);
			minigolfHoleEntry.layout(i);

			vHoles.addView(minigolfHoleEntry.getView());
		}

		vPlayers = (LinearLayout) view.findViewById(R.id.minigolf_players);

		vPlayerScores = new MinigolfPlayerScore[mPlayers.length];
		for(int i=0 ; i<mPlayers.length ; i++) {
			MinigolfPlayerScore playerScore = MinigolfPlayerScore.inflate(getMyActivity(), vPlayers, i, NUM_HOLES);
			playerScore.getAvatar().layoutAvatar(null, mPlayers[i]);

			vPlayers.addView(playerScore.getView());
			vPlayerScores[i] = playerScore;
		}
	}

}
