package com.pp.xt3;

/**
 * Hello world!
 *
 */
import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.speech.v1.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;
import io.grpc.StatusRuntimeException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        // Instantiates a client
        try (SpeechClient speechClient = SpeechClient.create()) {

            // The path to the audio file to transcribe
            //String fileName = "./resources/freeweightsaudio.flac";
            String fileName = "https://storage.cloud.google.com/video2text_videos/freeweightsaudio.flac";
            String[] fileNames = {"gs://video2text_videos/acute-back-pain-1080p.mp4.flac",
                    "gs://video2text_videos/acute-vs-chronic-leukemia-1080p.mp4.flac",
                    "gs://video2text_videos/air-fryers-1080p.mp4.flac",
                    "gs://video2text_videos/ankylosing-spondylitis-how-diagnosed-1080p.mp4.flac",
                    "gs://video2text_videos/ankylosing-spondylitis-treatment-options-2-1080p.mp4.flac",
                    "gs://video2text_videos/ankylosing-spondylitis-what-is-as-1080p.mp4.flac",
                    "gs://video2text_videos/antidepressants-sex-drive-1080p.mp4.flac",
                    "gs://video2text_videos/apps-for-health-goals-1080p.mp4.flac",
                    "gs://video2text_videos/asparagus-pee-1080p.mp4.flac",
                    "gs://video2text_videos/autism_symptoms-1080p.mp4.flac",
                    "gs://video2text_videos/bad-breath-1080p.mp4.flac",
                    "gs://video2text_videos/bad-posture-health-risks-1080p.mp4.flac",
                    "gs://video2text_videos/better-sleep-during-menopause-1080p.mp4.flac",
                    "gs://video2text_videos/biopolar-vs-depression--1080p.mp4.flac",
                    "gs://video2text_videos/bipolar-symptoms-1080p.mp4.flac",
                    "gs://video2text_videos/bipolar-treatment-fix-1080p.mp4.flac",
                    "gs://video2text_videos/bipolar1_vs_bipolar2-1080p.mp4.flac",
                    "gs://video2text_videos/bipolar_diagnosed-1080p.mp4.flac",
                    "gs://video2text_videos/blood-cancer-treatments-1080p.mp4.flac",
                    "gs://video2text_videos/blood-cancer-understanding-main-types-1080p.mp4.flac",
                    "gs://video2text_videos/blood-facts-1080p.mp4.flac",
                    "gs://video2text_videos/bra-shopping-with-breast-cancer-v2-1080p.mp4.flac",
                    "gs://video2text_videos/bras-by-breast-surgery-v2-1080p.mp4.flac",
                    "gs://video2text_videos/breast-cancer-surgery-myths-1080p.mp4.flac",
                    "gs://video2text_videos/calcium-supplements-1080p.mp4.flac",
                    "gs://video2text_videos/cancer-survivor-finds-inspiration-1080p.mp4.flac",
                    "gs://video2text_videos/caregiver-coping-with-burnout-2-1080p.mp4.flac",
                    "gs://video2text_videos/caregivers-migraines-1080p.mp4.flac",
                    "gs://video2text_videos/caring-for-someone-with-memory-loss-1080p.mp4.flac",
                    "gs://video2text_videos/causes-excess-burping-1080p.mp4.flac",
                    "gs://video2text_videos/causes-heavy-menstrual-flow-1080p.mp4.flac",
                    "gs://video2text_videos/celiac-disease-vs-gluten-intolerance-1080p.mp4.flac",
                    "gs://video2text_videos/cellulite-myths-1080p.mp4.flac",
                    "gs://video2text_videos/claytor-stretches-after-breast-surgery-1080p.mp4.flac",
                    "gs://video2text_videos/clean-ears-cotton-swab-1080p.mp4.flac",
                    "gs://video2text_videos/clitoris-facts-1080p.mp4.flac",
                    "gs://video2text_videos/cll-leukemia-diagnosis-1080p.mp4.flac",
                    "gs://video2text_videos/cll-sll-differences-1080p.mp4.flac",
                    "gs://video2text_videos/coffee-dehydration-myth-1080p.mp4.flac",
                    "gs://video2text_videos/common-symptoms-of-uc-1080p.mp4.flac",
                    "gs://video2text_videos/conditions-susceptible-heat-stroke-1080p.mp4.flac",
                    "gs://video2text_videos/cooking-safety-thanksgiving-turkey-1080p.mp4.flac",
                    "gs://video2text_videos/dark-circles-home-remedies-1080p.mp4.flac",
                    "gs://video2text_videos/deafening-silence-of-depression-1080p.mp4.flac",
                    "gs://video2text_videos/dentist_favorite_toothbrush-1080p.mp4.flac",
                    "gs://video2text_videos/diet-lower-cancer-risk-1080p.mp4.flac",
                    "gs://video2text_videos/doctor-pronunciation-1080p.mp4.flac",
                    "gs://video2text_videos/does-hot-water-kill-more-germs-1080p.mp4.flac",
                    "gs://video2text_videos/does-warm-milk-help-you-sleep-1080p.mp4.flac",
                    "gs://video2text_videos/dog-thanksgiving-mistake-1080p.mp4.flac",
                    "gs://video2text_videos/duct-tape-wart-removal-1080p.mp4.flac",
                    "gs://video2text_videos/during-migraine-1080p.mp4.flac",
                    "gs://video2text_videos/eat-salmon-for-younger-skin-1080p.mp4.flac",
                    "gs://video2text_videos/eyelid-spasm-causes-1080p.mp4.flac",
                    "gs://video2text_videos/fall-back-asleep-v2-1080p.mp4.flac",
                    "gs://video2text_videos/fast-healthy-breakfast-ideas-1080p.mp4.flac",
                    "gs://video2text_videos/fibromyalgia-symptoms-rev-1080p.mp4.flac",
                    "gs://video2text_videos/fight-off-bed-bugs-1080p.mp4.flac",
                    "gs://video2text_videos/firework-accidents-1080p.mp4.flac",
                    "gs://video2text_videos/fitness-is-also-mental-v2-1080p.mp4.flac",
                    "gs://video2text_videos/five-self-care-tips-for-blood-cancer-1080p.mp4.flac",
                    "gs://video2text_videos/fodmap-diet-1080p.mp4.flac",
                    "gs://video2text_videos/food-before-after-workouts-v3-1080p.mp4.flac",
                    "gs://video2text_videos/food-journal-tips-1080p.mp4.flac",
                    "gs://video2text_videos/foods-exceed-sugar-limit-1080p.mp4.flac",
                    "gs://video2text_videos/foods-hard-to-pronounce-1080p.mp4.flac",
                    "gs://video2text_videos/foods-that-age-your-skin-1080p.mp4.flac",
                    "gs://video2text_videos/foods-that-burn-calories-1080p.mp4.flac",
                    "gs://video2text_videos/foods-with-tree-nuts-1080p.mp4.flac",
                    "gs://video2text_videos/freeweightsaudio.flac",
                    "gs://video2text_videos/fruit-infused-water-1080p.mp4.flac",
                    "gs://video2text_videos/fruits-you-thought-were-veggies-1080p.mp4.flac",
                    "gs://video2text_videos/garlic-health-benefits-1080p.mp4.flac",
                    "gs://video2text_videos/genius-slow-cooker-hacks-1080p.mp4.flac",
                    "gs://video2text_videos/gerd-diagnosis-1080p.mp4.flac",
                    "gs://video2text_videos/getting-rid-of-backfat-v2-1080p.mp4.flac",
                    "gs://video2text_videos/gingivits-mistake-1080p.mp4.flac",
                    "gs://video2text_videos/habits-for-good-sleep-1080p.mp4.flac",
                    "gs://video2text_videos/habits-that-cause-back-pain-1080p.mp4.flac",
                    "gs://video2text_videos/habits-that-trigger-flares-1080p.mp4.flac",
                    "gs://video2text_videos/hair-nails-after-death-1080p.mp4.flac",
                    "gs://video2text_videos/halloween-food-allergy-trade-method-1080p.mp4.flac",
                    "gs://video2text_videos/harmful-health-goals-1080p.mp4.flac",
                    "gs://video2text_videos/health-benefits-microgreens-1080p.mp4.flac",
                    "gs://video2text_videos/health-benefits-oat-milk-1080p.mp4.flac",
                    "gs://video2text_videos/health-benefits-oatmeal-1080p.mp4.flac",
                    "gs://video2text_videos/health-benefits-of-pumpkin-1080p.mp4.flac",
                    "gs://video2text_videos/health-benefits-of-turmeric-1080p.mp4.flac",
                    "gs://video2text_videos/health-benefits-pickles-1080p.mp4.flac",
                    "gs://video2text_videos/health-goals-besides-weight-loss-1080p.mp4.flac",
                    "gs://video2text_videos/health-problems-during-period-1080p.mp4.flac",
                    "gs://video2text_videos/healthier-eggnog-1080p.mp4.flac",
                    "gs://video2text_videos/healthy-desserts-1080p.mp4.flac",
                    "gs://video2text_videos/healthy-fall-foods-1080p.mp4.flac",
                    "gs://video2text_videos/healthy-halloween-treats-1080p.mp4.flac",
                    "gs://video2text_videos/healthy-high-calorie-foods-1080p.mp4.flac",
                    "gs://video2text_videos/healthy-restaurant-options-1080p.mp4.flac",
                    "gs://video2text_videos/healthy-toast-toppings-1080p.mp4.flac",
                    "gs://video2text_videos/heart-failure-body-1080p.mp4.flac",
                    "gs://video2text_videos/heart-failure-surgical-treatments-1080p.mp4.flac",
                    "gs://video2text_videos/heartburn-foods-rev-1080p.mp4.flac",
                    "gs://video2text_videos/heartburn-myths-1080p.mp4.flac",
                    "gs://video2text_videos/heat-cramps-2-1080p.mp4.flac",
                    "gs://video2text_videos/holiday-candle-safety-1080p.mp4.flac",
                    "gs://video2text_videos/hollys-advice-on-how-to-meditate-v3-1080p.mp4.flac",
                    "gs://video2text_videos/home-migraine-remedies-1080p.mp4.flac",
                    "gs://video2text_videos/honey-vs-table-sugar-1080p.mp4.flac",
                    "gs://video2text_videos/hookah-myth-1080p.mp4.flac",
                    "gs://video2text_videos/hospice-vs-palliative-care-v3-1080p.mp4.flac",
                    "gs://video2text_videos/how-bad-is-it-sitting-all-day-1080p.mp4.flac",
                    "gs://video2text_videos/how-biologics-work-1080p.mp4.flac",
                    "gs://video2text_videos/how-doctors-diagnose-uc-v2-1080p.mp4.flac",
                    "gs://video2text_videos/how-holly-got-into-meditation-1080p.mp4.flac",
                    "gs://video2text_videos/how-holly-meditates-1080p.mp4.flac",
                    "gs://video2text_videos/how-long-to-brush-your-teeth-1080p.mp4.flac",
                    "gs://video2text_videos/how-many-times-strength-training-1080p.mp4.flac",
                    "gs://video2text_videos/how-many-times-week-workout-1080p.mp4.flac",
                    "gs://video2text_videos/how-rare-is-your-blood-type-1080p.mp4.flac",
                    "gs://video2text_videos/how-to-get-pallative-care-1080p.mp4.flac",
                    "gs://video2text_videos/how-to-pick-the-right-sunglasses-1080p.mp4.flac",
                    "gs://video2text_videos/how-to-treat-a-sunburn-1080p.mp4.flac",
                    "gs://video2text_videos/ice-cube-tray-hacks-1080p.mp4.flac",
                    "gs://video2text_videos/iceberg-lettuce-1080p.mp4.flac",
                    "gs://video2text_videos/iced-coffee-hacks-v2-1080p.mp4.flac",
                    "gs://video2text_videos/importance-of-meditation-v3-1080p.mp4.flac",
                    "gs://video2text_videos/importance-of-wearing-sunglasses-1080p.mp4.flac",
                    "gs://video2text_videos/is-maple-water-healthy-1080p.mp4.flac",
                    "gs://video2text_videos/is-msg-safe-to-eat-1080p.mp4.flac",
                    "gs://video2text_videos/is-vocal-fry-safe-1080p.mp4.flac",
                    "gs://video2text_videos/jenna-leveille-weight-loss-success-story-1080p.mp4.flac",
                    "gs://video2text_videos/kid-friendly-snacks-1080p.mp4.flac",
                    "gs://video2text_videos/lactose-intolerance-vs-dairy-allergy-1080p.mp4.flac",
                    "gs://video2text_videos/laughter-improve-sleep-1080p.mp4.flac",
                    "gs://video2text_videos/lemon-motion-sickness-1080p.mp4.flac",
                    "gs://video2text_videos/leukemia-caregiving-loved-one-1080p.mp4.flac",
                    "gs://video2text_videos/leukemia-signs-symptoms-diagnosis-1080p.mp4.flac",
                    "gs://video2text_videos/light-therapy-seasonal-affective-disorder-1080p.mp4.flac",
                    "gs://video2text_videos/lightning-car-safe-1080p.mp4.flac",
                    "gs://video2text_videos/live-14-years-longer-1080p.mp4.flac",
                    "gs://video2text_videos/lose-weight-not-bone-1080p.mp4.flac",
                    "gs://video2text_videos/lose-weight-with-led-foods-1080p.mp4.flac",
                    "gs://video2text_videos/low-calorie-thanksgiving-sides-1080p.mp4.flac",
                    "gs://video2text_videos/lung-cancer-advanced-diagnosis-2-1080p.mp4.flac",
                    "gs://video2text_videos/lung-cancer-caregiving-loved-ones-1080p.mp4.flac",
                    "gs://video2text_videos/lung-cancer-coping-smoking-stigma-1080p.mp4.flac",
                    "gs://video2text_videos/lung-cancer-immunotherapy-1080p.mp4.flac",
                    "gs://video2text_videos/lung-cancer-important-quit-smoking-1080p.mp4.flac",
                    "gs://video2text_videos/lung-cancer-proper-nutrition-1080p.mp4.flac",
                    "gs://video2text_videos/lung-cancer-targeted-therapy-1080p.mp4.flac",
                    "gs://video2text_videos/lyme-disease-symptoms-1080p.mp4.flac",
                    "gs://video2text_videos/male-pattern-baldness-causes-1080p.mp4.flac",
                    "gs://video2text_videos/massage-health-benefits-1080p.mp4.flac",
                    "gs://video2text_videos/matcha-lattes-1080p.mp4.flac",
                    "gs://video2text_videos/medical-bad-breath-1080p.mp4.flac",
                    "gs://video2text_videos/medical-treatments-for-psa-final-1080p.mp4.flac",
                    "gs://video2text_videos/medications-for-high-cholesterol-1080p.mp4.flac",
                    "gs://video2text_videos/mental-health-goals-1080p.mp4.flac",
                    "gs://video2text_videos/metastatic-breast-cancer-immunotherapy-1080p.mp4.flac",
                    "gs://video2text_videos/migraine-myths-v2-1080p.mp4.flac",
                    "gs://video2text_videos/migraine-treatments-options-1080p.mp4.flac",
                    "gs://video2text_videos/migraine-triggers-1080p.mp4.flac",
                    "gs://video2text_videos/migraines-vs-headchae-v6-1080p.mp4.flac",
                    "gs://video2text_videos/migraines-warning-signs-1080p.mp4.flac",
                    "gs://video2text_videos/missed-germy-spot-1080p.mp4.flac",
                    "gs://video2text_videos/moms-of-breast-cancer-patients-v2-1080p.mp4.flac",
                    "gs://video2text_videos/money-saving-grocery-hacks-1080p.mp4.flac",
                    "gs://video2text_videos/morning_bad_breath-1080p.mp4.flac",
                    "gs://video2text_videos/morning_rituals_frances_lg_final-1080p.mp4.flac",
                    "gs://video2text_videos/multiple-sclerosis-lori-schneider-coped-diagnosis-1080p.mp4.flac",
                    "gs://video2text_videos/multiple-sclerosis-meet-lori-schneider-1080p.mp4.flac",
                    "gs://video2text_videos/muscle-foods-1080p.mp4.flac",
                    "gs://video2text_videos/my-toolkit-for-living-with-depression-1080p.mp4.flac",
                    "gs://video2text_videos/myths-about-osteo-workouts-1080p.mp4.flac",
                    "gs://video2text_videos/myths-about-palliative-care-v3-1080p.mp4.flac",
                    "gs://video2text_videos/myths_about_whitening-1080p.mp4.flac",
                    "gs://video2text_videos/nasal-spray-flu-vaccine-1080p.mp4.flac",
                    "gs://video2text_videos/nighttime-heartburn-1080p.mp4.flac",
                    "gs://video2text_videos/no-aspirin-during-stroke-1080p.mp4.flac",
                    "gs://video2text_videos/nootropic-foods-1080p.mp4.flac",
                    "gs://video2text_videos/not-liking-burpees-1080p.mp4.flac",
                    "gs://video2text_videos/not_visting_dentist-1080p.mp4.flac",
                    "gs://video2text_videos/oatmeal-mistakes-1080p.mp4.flac",
                    "gs://video2text_videos/olive-oil-vs-evoo-1080p.mp4.flac",
                    "gs://video2text_videos/paleo-diet-1080p.mp4.flac",
                    "gs://video2text_videos/pee-on-jellyfish-sting-1080p.mp4.flac",
                    "gs://video2text_videos/pink-eye-signs-1080p.mp4.flac",
                    "gs://video2text_videos/plant_based_diet_v2-1080p.mp4.flac",
                    "gs://video2text_videos/poinsettias-risk-pets-1080p.mp4.flac",
                    "gs://video2text_videos/probiotics-with-a-gi-final-1080p.mp4.flac",
                    "gs://video2text_videos/proper-nutrition-blood-cancer-v2-1080p.mp4.flac",
                    "gs://video2text_videos/psa_diagnosis-1080p.mp4.flac",
                    "gs://video2text_videos/psoriasis-treatments-v2-1080p.mp4.flac",
                    "gs://video2text_videos/questions-for-your-doctor-1080p.mp4.flac",
                    "gs://video2text_videos/rapid-stroke-treatment-window-1080p.mp4.flac",
                    "gs://video2text_videos/rem-sleep-facts-1080p.mp4.flac",
                    "gs://video2text_videos/rheumatoid-arthritis-common-medication-mistakes-1080p.mp4.flac",
                    "gs://video2text_videos/rheumatoid-arthritis-medication-options-1080p.mp4.flac",
                    "gs://video2text_videos/rheumatoid-arthritis-prevent-flares-1080p.mp4.flac",
                    "gs://video2text_videos/rheumatoid-arthritis-signs-treatment-working-1080p.mp4.flac",
                    "gs://video2text_videos/rheumatoid-arthritis-warm-water-remedy-1080p.mp4.flac",
                    "gs://video2text_videos/sad-girls-club-mental-health-community-v2-1080p.mp4.flac",
                    "gs://video2text_videos/salad-mistakes-1080p.mp4.flac",
                    "gs://video2text_videos/salt-water-sore-throat-1080p.mp4.flac",
                    "gs://video2text_videos/same-workout-two-days-1080p.mp4.flac",
                    "gs://video2text_videos/sex-during-breast-cancer-v2-1080p.mp4.flac",
                    "gs://video2text_videos/sexual-health-words-1080p.mp4.flac",
                    "gs://video2text_videos/should-you-starve-a-fever-1080p.mp4.flac",
                    "gs://video2text_videos/sign-caregiver-needs-break-1080p.mp4.flac",
                    "gs://video2text_videos/signs-heat-stroke-dogs-1080p.mp4.flac",
                    "gs://video2text_videos/signs-of-burnout-1080p.mp4.flac",
                    "gs://video2text_videos/signs-of-gum-disease-1080p.mp4.flac",
                    "gs://video2text_videos/signs-symptoms-of-as-1080p.mp4.flac",
                    "gs://video2text_videos/smelly-pee-causes-1080p.mp4.flac",
                    "gs://video2text_videos/sneaky-portion-sizes-1080p.mp4.flac",
                    "gs://video2text_videos/sneaky-scalp-sunburn-1080p.mp4.flac",
                    "gs://video2text_videos/snot-color-1080p.mp4.flac",
                    "gs://video2text_videos/social-media-break-1080p.mp4.flac",
                    "gs://video2text_videos/sources-of-vitamin-a-1080p.mp4.flac",
                    "gs://video2text_videos/strength-trainging-first-or-cardio-1080p.mp4.flac",
                    "gs://video2text_videos/stroke-diagnosis-1080p.mp4.flac",
                    "gs://video2text_videos/stroke-prevention-1080p.mp4.flac",
                    "gs://video2text_videos/suicide-and-depression-stigma-v2-1080p.mp4.flac",
                    "gs://video2text_videos/surprising-low-cal-foods-2-1080p.mp4.flac",
                    "gs://video2text_videos/symptoms_of_psoriatic_arthritis-1080p.mp4.flac",
                    "gs://video2text_videos/tahini-benefits-1080p.mp4.flac",
                    "gs://video2text_videos/takeaways-from-fad-diets-1080p.mp4.flac",
                    "gs://video2text_videos/tanning-stretch-marks-myths-1080p.mp4.flac",
                    "gs://video2text_videos/targeted-therapies-for-leukemia-1080p.mp4.flac",
                    "gs://video2text_videos/tattoo-care-beach-1080p.mp4.flac",
                    "gs://video2text_videos/teeth-grinding-1080p.mp4.flac",
                    "gs://video2text_videos/teeth-staining-foods-1080p.mp4.flac",
                    "gs://video2text_videos/teeth-whitening-1080p.mp4.flac",
                    "gs://video2text_videos/thanksgiving-healthy-hacks-1080p.mp4.flac",
                    "gs://video2text_videos/thanksgiving-leftovers-ideas-1080p.mp4.flac",
                    "gs://video2text_videos/three-stroke-myths-1080p.mp4.flac",
                    "gs://video2text_videos/tick-removal-nail-polish-1080p.mp4.flac",
                    "gs://video2text_videos/tinsel-and-cats-1080p.mp4.flac",
                    "gs://video2text_videos/tips-stop-yo-yo-dieting-1080p.mp4.flac",
                    "gs://video2text_videos/toothbrush-rule-1080p.mp4.flac",
                    "gs://video2text_videos/travel-health-kit-1080p.mp4.flac",
                    "gs://video2text_videos/travelers-diarrhea-v2-1080p.mp4.flac",
                    "gs://video2text_videos/treadmill-elliptical-bike-v2-1080p.mp4.flac",
                    "gs://video2text_videos/treat-a-mosquito-bite-1080p.mp4.flac",
                    "gs://video2text_videos/treatment-during-remission-1080p.mp4.flac",
                    "gs://video2text_videos/treatment-options-for-uc-1080p.mp4.flac",
                    "gs://video2text_videos/truth-about-cavaties-1080p.mp4.flac",
                    "gs://video2text_videos/types-of-back-pain--1080p.mp4.flac",
                    "gs://video2text_videos/types-winter-squash-1080p.mp4.flac",
                    "gs://video2text_videos/uc-complications-v4-1080p.mp4.flac",
                    "gs://video2text_videos/uc-vs-crohns-disease-v2-1080p.mp4.flac",
                    "gs://video2text_videos/understanding-stroke-women-1080p.mp4.flac",
                    "gs://video2text_videos/understanding-tia-1080p.mp4.flac",
                    "gs://video2text_videos/using-free-weights-or-machines-1080p.mp4.flac",
                    "gs://video2text_videos/vagina-pain-during-sex-1080p.mp4.flac",
                    "gs://video2text_videos/ways-psa-affects-your-body-final-1080p.mp4.flac",
                    "gs://video2text_videos/what-fingernails-say-about-psa-final-1080p.mp4.flac",
                    "gs://video2text_videos/what-is-art-therapy-1080p.mp4.flac",
                    "gs://video2text_videos/what-is-cbd-oil-v2-1080p.mp4.flac",
                    "gs://video2text_videos/what-is-health-anxiety-1080p.mp4.flac",
                    "gs://video2text_videos/what-is-heartburn-1080p.mp4.flac",
                    "gs://video2text_videos/what-is-kombucha-1080p.mp4.flac",
                    "gs://video2text_videos/what-is-leukemia-v2-1080p.mp4.flac",
                    "gs://video2text_videos/what-is-migraine-v2-1080p.mp4.flac",
                    "gs://video2text_videos/what-is-palliative-care-v2-1080p.mp4.flac",
                    "gs://video2text_videos/what-is-swimmers-ear-1080p.mp4.flac",
                    "gs://video2text_videos/what-is-uc-1080p.mp4.flac",
                    "gs://video2text_videos/what-to-eat-during-a-flare-1080p.mp4.flac",
                    "gs://video2text_videos/what-to-wear-post-mastectomy-v3-1080p.mp4.flac",
                    "gs://video2text_videos/what-you-eat-doesnt-cause-uc-1080p.mp4.flac",
                    "gs://video2text_videos/whitening-home-remedy-final-1080p.mp4.flac",
                    "gs://video2text_videos/why-blood-is-red-1080p.mp4.flac",
                    "gs://video2text_videos/why-cilantro-tastes-like-soap-1080p.mp4.flac",
                    "gs://video2text_videos/winter-activities-1080p.mp4.flac",
                    "gs://video2text_videos/work-off-thanksgiving-dinner-1080p.mp4.flac",
                    "gs://video2text_videos/young-people-stroke-1080p.mp4.flac"};

            // Reads the audio file into memory
            /*Path path = Paths.get(fileName);
            byte[] data = Files.readAllBytes(path);
            ByteString audioBytes = ByteString.copyFrom(data);*/

            Gson gson = new Gson();
            gson = gson.newBuilder().setPrettyPrinting().create();
            List<VideoTranscript> transcriptsList = new ArrayList<VideoTranscript>();

            // Builds the sync recognize request
            for(String videoFile : fileNames){
                RecognitionConfig config =
                        RecognitionConfig.newBuilder()
                                .setEncoding(RecognitionConfig.AudioEncoding.FLAC)
                                .setSampleRateHertz(48000)
                                .setAudioChannelCount(2)
                                .setLanguageCode("en-US")
                                .build();
                RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(videoFile).build();

                try {
                    // Performs speech recognition on the audio file
                    OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> response = speechClient.longRunningRecognizeAsync(config, audio);
                    while (!response.isDone()) {
                        System.out.println("Waiting for response...");
                        Thread.sleep(10000);
                    }

                    List<SpeechRecognitionResult> results = response.get().getResultsList();

                    String transcript = "";
                    for (SpeechRecognitionResult result : results) {
                        // There can be several alternative transcripts for a given chunk of speech. Just use the
                        // first (most likely) one here.
                        SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                        System.out.printf("Transcription: %s\n", alternative.getTranscript());
                        transcript = transcript + alternative.getTranscript();
                    }
                    VideoTranscript videoTranscript = new VideoTranscript();
                    videoTranscript.setVideoFile(videoFile);
                    videoTranscript.setTranscript(transcript);
                    transcriptsList.add(videoTranscript);
                }catch (Exception st){
                    st.printStackTrace();
                }
            }

            System.out.println(gson.toJson(transcriptsList));
        }
    }
}

class VideoTranscript {
    private String videoFile;
    private String transcript;

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }
}
