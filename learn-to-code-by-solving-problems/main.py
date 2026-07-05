from pathlib import Path
import logging

logger = logging.getLogger(__name__)
logging.basicConfig(level=logging.DEBUG,force=True)

chapters = [
    "01_getting_started",
    "02_making_decisions",
    "03_repeating_code_definite_loops",
    "04_repeating_code_indefinite_loops",
    "05_organizing_values_using_lists",
    "06_designing_programs_with_functions",
    "07_reading_and_writing_files",
    "08_organizing_values_using_sets_and_dictionaries",
    "09_designing_algorithms_with_complete_search",
    "10_big_o_and_program_efficiency",
]

for folder in chapters:
    fold_path = Path(__file__).parent / folder

    if not fold_path.exists():
        fold_path.mkdir()
        if logger.isEnabledFor(logging.DEBUG):
            logger.debug(f"create {folder = }")




