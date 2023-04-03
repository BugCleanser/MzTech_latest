/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 */
package mz.tech.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import mz.tech.util.TaskUtil;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;

public class MidiUtil {
    private static final String info = "From SkQuery";
    private static final byte[] instruments;
    private static HashMap<Object, Sequencer> playing;

    static {
        byte[] byArray = new byte[128];
        byArray[7] = 11;
        byArray[8] = 6;
        byArray[9] = 6;
        byArray[10] = 6;
        byArray[11] = 6;
        byArray[12] = 9;
        byArray[13] = 9;
        byArray[14] = 15;
        byArray[15] = 11;
        byArray[16] = 10;
        byArray[17] = 5;
        byArray[18] = 5;
        byArray[19] = 10;
        byArray[20] = 10;
        byArray[21] = 10;
        byArray[22] = 10;
        byArray[23] = 10;
        byArray[24] = 5;
        byArray[25] = 5;
        byArray[26] = 5;
        byArray[27] = 5;
        byArray[28] = 5;
        byArray[29] = 5;
        byArray[30] = 5;
        byArray[31] = 5;
        byArray[32] = 1;
        byArray[33] = 1;
        byArray[34] = 1;
        byArray[35] = 1;
        byArray[36] = 1;
        byArray[37] = 1;
        byArray[38] = 1;
        byArray[39] = 1;
        byArray[41] = 10;
        byArray[42] = 10;
        byArray[43] = 1;
        byArray[47] = 4;
        byArray[52] = 8;
        byArray[53] = 8;
        byArray[54] = 8;
        byArray[55] = 12;
        byArray[56] = 8;
        byArray[57] = 14;
        byArray[58] = 14;
        byArray[59] = 14;
        byArray[60] = 14;
        byArray[61] = 14;
        byArray[62] = 8;
        byArray[63] = 8;
        byArray[64] = 8;
        byArray[65] = 8;
        byArray[66] = 8;
        byArray[67] = 14;
        byArray[68] = 14;
        byArray[69] = 8;
        byArray[70] = 8;
        byArray[71] = 8;
        byArray[72] = 8;
        byArray[73] = 8;
        byArray[74] = 8;
        byArray[75] = 8;
        byArray[76] = 14;
        byArray[77] = 8;
        byArray[78] = 8;
        byArray[79] = 8;
        byArray[80] = 8;
        byArray[81] = 14;
        byArray[82] = 8;
        byArray[83] = 8;
        byArray[84] = 5;
        byArray[85] = 8;
        byArray[86] = 12;
        byArray[87] = 1;
        byArray[88] = 1;
        byArray[91] = 8;
        byArray[98] = 7;
        byArray[103] = 12;
        byArray[104] = 11;
        byArray[105] = 11;
        byArray[106] = 3;
        byArray[107] = 3;
        byArray[108] = 3;
        byArray[109] = 14;
        byArray[110] = 10;
        byArray[111] = 6;
        byArray[112] = 6;
        byArray[113] = 3;
        byArray[114] = 3;
        byArray[115] = 2;
        byArray[116] = 2;
        byArray[117] = 2;
        byArray[118] = 6;
        byArray[119] = 5;
        byArray[120] = 1;
        byArray[121] = 1;
        byArray[122] = 1;
        byArray[123] = 13;
        byArray[124] = 13;
        byArray[125] = 2;
        byArray[126] = 4;
        byArray[127] = 7;
        instruments = byArray;
        playing = new HashMap();
    }

    private static byte byteInstrument(int patch) {
        if (patch < 0 || patch >= instruments.length) {
            return 0;
        }
        return instruments[patch];
    }

    public static Sound patchToInstrument(int patch) {
        return Instrument.fromByte(MidiUtil.byteInstrument(patch)).getSound();
    }

    public static void stop(Object id) {
        if (playing.containsKey(id)) {
            Sequencer sequencer = playing.get(id);
            try {
                sequencer.getReceiver().close();
            }
            catch (MidiUnavailableException midiUnavailableException) {
                // empty catch block
            }
            sequencer.stop();
            sequencer.close();
            playing.remove(id);
        }
    }

    public static Sound soundAttempt(String attempt, String fallback) {
        Sound sound = null;
        try {
            sound = Sound.valueOf((String)attempt);
        }
        catch (Exception e) {
            try {
                sound = Sound.valueOf((String)fallback);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (sound == null) {
            sound = Sound.ENTITY_PLAYER_LEVELUP;
        }
        return sound;
    }

    public static void playMidi(Sequence sequence, float tempo, Object ID, PlaySoundAble ... listeners) {
        MidiUtil.stop(ID);
        try {
            Sequencer sequencer = MidiSystem.getSequencer(false);
            sequencer.setSequence(sequence);
            sequencer.open();
            sequencer.setTempoFactor(tempo);
            NoteBlockReceiver reciever = new NoteBlockReceiver(ID, listeners);
            sequencer.getTransmitter().setReceiver(reciever);
            sequencer.start();
            playing.put(ID, sequencer);
        }
        catch (InvalidMidiDataException | MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public static void playMidi(InputStream stream, float tempo, Object ID, PlaySoundAble ... listeners) {
        TaskUtil.throwRuntime(() -> MidiUtil.playMidi(MidiSystem.getSequence(stream), tempo, ID, listeners));
    }

    public static void stopAll() {
        playing.forEach((id, s) -> s.stop());
    }

    public static boolean isPlaying(Object key) {
        return playing.containsKey(key);
    }

    public static enum Instrument {
        PIANO(0, "BLOCK_NOTE_BLOCK_HARP", "BLOCK_NOTE_HARP", "NOTE_PIANO"),
        BASS(1, "BLOCK_NOTE_BLOCK_BASS", "BLOCK_NOTE_BASS", "NOTE_BASS"),
        SNARE_DRUM(2, "BLOCK_NOTE_BLOCK_SNARE", "BLOCK_NOTE_SNARE", "NOTE_SNARE_DRUM"),
        STICKS(3, "BLOCK_NOTE_BLOCK_HAT", "BLOCK_NOTE_HAT", "NOTE_STICKS"),
        BASE_DRUM(4, "BLOCK_NOTE_BLOCK_BASEDRUM", "BLOCK_NOTE_BASEDRUM", "NOTE_BASS_DRUM"),
        GUITAR(5, "BLOCK_NOTE_BLOCK_GUITAR", "BLOCK_NOTE_GUITAR", "NOTE_BASS_GUITAR"),
        BELL(6, "BLOCK_NOTE_BLOCK_BELL", "BLOCK_NOTE_BELL", "NOTE_PIANO"),
        CHIME(7, "BLOCK_NOTE_BLOCK_CHIME", "BLOCK_NOTE_CHIME", "NOTE_PIANO"),
        FLUTE(8, "BLOCK_NOTE_BLOCK_FLUTE", "BLOCK_NOTE_FLUTE", "NOTE_PIANO"),
        XYLOPHONE(9, "BLOCK_NOTE_BLOCK_XYLOPHONE", "BLOCK_NOTE_XYLOPHONE", "NOTE_STICKS"),
        PLING(10, "BLOCK_NOTE_BLOCK_PLING", "BLOCK_NOTE_PLING", "NOTE_PIANO"),
        BANJO(11, "BLOCK_NOTE_BLOCK_BANJO", "BLOCK_NOTE_BLOCK_GUITAR", "BLOCK_NOTE_BASS_GUITAR"),
        BIT(12, "BLOCK_NOTE_BLOCK_BIT", "BLOCK_NOTE_BLOCK_PLING", "BLOCK_NOTE_PIANO"),
        COW_BELL(13, "BLOCK_NOTE_BLOCK_COW_BELL", "BLOCK_NOTE_BLOCK_BELL", "BLOCK_NOTE_BELL"),
        DIDGERIDOO(14, "BLOCK_NOTE_BLOCK_DIDGERIDOO", "BLOCK_NOTE_BLOCK_BASS", "BLOCK_NOTE_BASS"),
        IRON_XYLOPHONE(15, "BLOCK_NOTE_BLOCK_IRON_XYLOPHONE", "BLOCK_NOTE_BLOCK_XYLOPHONE", "BLOCK_NOTE_XYLOPHONE");

        private final int pitch;
        private Sound sound;

        private Instrument(int pitch, String sound, String fallback, String old) {
            this.sound = MidiUtil.soundAttempt(sound, fallback);
            if (sound == null) {
                this.sound = MidiUtil.soundAttempt(old, fallback);
            }
            this.pitch = pitch;
        }

        public static Instrument fromByte(byte instrument) {
            switch (instrument) {
                case 1: {
                    return BASS;
                }
                case 2: {
                    return SNARE_DRUM;
                }
                case 3: {
                    return STICKS;
                }
                case 4: {
                    return BASE_DRUM;
                }
                case 5: {
                    return GUITAR;
                }
                case 6: {
                    return BELL;
                }
                case 7: {
                    return CHIME;
                }
                case 8: {
                    return FLUTE;
                }
                case 9: {
                    return XYLOPHONE;
                }
                case 10: {
                    return PLING;
                }
                case 11: {
                    return BANJO;
                }
                case 12: {
                    return BIT;
                }
                case 13: {
                    return COW_BELL;
                }
                case 14: {
                    return DIDGERIDOO;
                }
                case 15: {
                    return IRON_XYLOPHONE;
                }
            }
            return PIANO;
        }

        public Sound getSound() {
            return this.sound;
        }

        public byte getByte() {
            return (byte)this.pitch;
        }
    }

    public static class NoteBlockReceiver
    implements Receiver {
        private final Map<Integer, Integer> patches = new HashMap<Integer, Integer>();
        private final PlaySoundAble[] listeners;
        private final Object ID;

        public NoteBlockReceiver(Object ID, PlaySoundAble ... listeners) {
            this.listeners = listeners;
            this.ID = ID;
        }

        @Override
        public void send(MidiMessage midi, long time) {
            if (midi instanceof ShortMessage) {
                ShortMessage message = (ShortMessage)midi;
                int channel = message.getChannel();
                switch (message.getCommand()) {
                    case 192: {
                        int patch = message.getData1();
                        this.patches.put(channel, patch);
                        break;
                    }
                    case 144: {
                        float volume = 10.0f * ((float)message.getData2() / 127.0f);
                        float pitch = this.getNote(this.toMCNote(message.getData1()));
                        Sound instrument = Instrument.PIANO.getSound();
                        Optional<Integer> optional = Optional.ofNullable(this.patches.get(message.getChannel()));
                        if (optional.isPresent()) {
                            instrument = channel == 9 ? MidiUtil.patchToInstrument(optional.get()) : MidiUtil.patchToInstrument(optional.get());
                        }
                        PlaySoundAble[] playSoundAbleArray = this.listeners;
                        int n = this.listeners.length;
                        int n2 = 0;
                        while (n2 < n) {
                            PlaySoundAble player = playSoundAbleArray[n2];
                            player.playSound(instrument, volume, pitch);
                            ++n2;
                        }
                        break;
                    }
                }
            }
        }

        public float getNote(byte note) {
            return (float)Math.pow(2.0, (double)(note - 12) / 12.0);
        }

        private byte toMCNote(int n) {
            if (n < 54) {
                return (byte)((n - 6) % 12);
            }
            if (n > 78) {
                return (byte)((n - 6) % 12 + 12);
            }
            return (byte)(n - 54);
        }

        @Override
        public void close() {
            MidiUtil.stop((String)this.ID);
            this.patches.clear();
        }
    }

    public static interface PlaySoundAble {
        public void playSound(Sound var1, float var2, float var3);

        public static PlaySoundAble newPlaySoundAble(final OfflinePlayer player) {
            return new PlaySoundAble(){

                @Override
                public void playSound(Sound var1, float var2, float var3) {
                    if (player.isOnline()) {
                        player.getPlayer().playSound(player.getPlayer().getLocation(), var1, var2, var3);
                    }
                }
            };
        }

        public static PlaySoundAble newPlaySoundAble(final Location loc) {
            return new PlaySoundAble(){

                @Override
                public void playSound(Sound var1, float var2, float var3) {
                    loc.getWorld().playSound(loc, var1, var2, var3);
                }
            };
        }
    }
}

